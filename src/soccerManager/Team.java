package soccerManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import soccerManager.objectTracking.ObjectIdentity;
import soccerManager.player.Attacker;
import soccerManager.player.Defender;
import soccerManager.player.Goalkeeper;
import soccerManager.player.PlayerBase;

public class Team extends ObjectIdentity {
    private final String Name;
    private final ArrayList<Goal> Goals;
    private final ArrayList<Game> Games;
    private final ArrayList<PlayerBase> Players;

    public String getName(){
        return this.Name;
    }

    public ArrayList<Goal> getGoals(){
        return this.Goals;
    }

    public ArrayList<PlayerBase> getPlayers(){
        return this.Players;
    }

    /**
     * Calculate the total team ability
     * @return sum of players ability
     */
    public int calculateAbility(){
        return this.Players.stream().map(g->g.calculateAbility()).mapToInt(Integer::intValue).sum();
    }

    /**
     * Find player by shirt number,
     * if not found return null
     * @param shirtNumber of player who you want find
     * @return null if not found and the player ref if found
     */
    private PlayerBase getPlayerByShirtNumber(int shirtNumber){
        var player = this.Players.stream().filter(p->p.getShirtNumber()==shirtNumber).findFirst();
        return player.isPresent() ? player.get() : null;
    }

    /**
     * Calculate sum of points of this team in all game history
     * @return sum of all team's games
     */
    public int getPoints(){
        return this.Games.stream().map(g->g.getResultPointsByTeam(this)).mapToInt(Integer::intValue).sum();
    }

    /**
     * Calculate the count of specified player type,
     * available amounts and types: 2 Attackers, 2 Defenders and 1 Goalkeeper
     * @param _class class of player type: Attacker, Defender and Goalkeeper
     * @return player type amount in team
     */
    private int getPlayerTypeCount(Class<?> _class){
        return (int)this.Players.stream().filter(p->p.getClass().isAssignableFrom(_class)).count();
    }

    /**
     * Add new player to team and set his shirt number,
     * the team needs to respect the types maximum quantities,
     * and require a new shirt number what not be used for another player
     * @param newPlayer who will be added to team
     * @param shirtNumber is the player shirt number
     */
    public void addPlayer(PlayerBase newPlayer, int shirtNumber){
        if(this.Players.size() == 5)
            throw new IllegalArgumentException("Team already have 5 players");
        if(this.Players.contains(newPlayer))
            throw new IllegalArgumentException("Player already is in the team");

        PlayerBase playerWithShirtNumber = getPlayerByShirtNumber(shirtNumber);
        if(playerWithShirtNumber != null)
            throw new IllegalArgumentException("Shirt number already is used");
            
        if(getPlayerTypeCount(Goalkeeper.class) == 1 && newPlayer.getClass().isAssignableFrom(Goalkeeper.class))
            throw new IllegalArgumentException("Maximum goalkeeper count");
        if(getPlayerTypeCount(Attacker.class) == 2 && newPlayer.getClass().isAssignableFrom(Attacker.class))
            throw new IllegalArgumentException("Maximum attacker count");
        if(getPlayerTypeCount(Defender.class) == 2 && newPlayer.getClass().isAssignableFrom(Defender.class))
            throw new IllegalArgumentException("Maximum defender count");

        this.Players.add(newPlayer);
        newPlayer.setCurrentTeam(this);
        newPlayer.setShirtNumber(shirtNumber);
    }

    /**
     * Add new player to team and give a shirt number,
     * the team needs to respect the types maximum quantities,
     * this function will find and set new number for the player's shirt
     * @param newPlayer who will be added to team
     */
    public void addPlayer(PlayerBase newPlayer){
        int shirtNumber = 1;
        Random generator = new Random(LocalDateTime.now().getNano());
        
        while(getPlayerByShirtNumber(shirtNumber) != null)
            shirtNumber = 1+(int)(generator.nextDouble() * 5);
        
        addPlayer(newPlayer, shirtNumber);
    }

    /**
     * Remove player from team using ref
     * @param oldPlayer ref of who player you want remove
     */
    public void removePlayer(PlayerBase oldPlayer){
        if(this.Players.contains(oldPlayer) == false)
            throw new IllegalArgumentException("Player not found!");
        
        this.Players.remove(oldPlayer);
        oldPlayer.setCurrentTeam(null);
    }

    /**
     * Find player by GUID and remove,
     * if not found throw exception
     * @param guid of player who you want remove
     */
    public void removePlayerByGUID(String guid){
        PlayerBase player = this.Players.stream().filter(p->p.getGUID()==guid).findFirst().get();

        if(player == null)
            throw new IllegalArgumentException("Player not found!");

        removePlayer(player);
    }

    /**
     * Find player by shirt number and remove,
     * if not found throw exception
     * @param guid of player who you want remove
     */
    public void removePlayerByShirtnumber(int shirtNumber){
        PlayerBase player = this.Players.stream().filter(p->p.getShirtNumber()==shirtNumber).findFirst().get();

        if(player == null)
            throw new IllegalArgumentException("Player not found!");

        removePlayer(player);
    }

    public Team(String name){
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("Name is null or empty");

        this.Name = name;
        this.Goals = new ArrayList<>();
        this.Games = new ArrayList<>();
        this.Players = new ArrayList<>();
    }
}
