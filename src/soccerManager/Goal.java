package soccerManager;

import soccerManager.objectTracking.*;
import soccerManager.player.*;

public class Goal extends ObjectIdentity {
    private final Team Team;
    private final Game Game;
    private final PlayerBase Player;

    public Game getGame(){
        return this.Game;
    }

    public Team getTeam(){
        return this.Team;
    }

    public PlayerBase getPlayer(){
        return this.Player;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("{\n");
        builder.append("    Game: ").append(getGame().toString()).append("\n");
        builder.append("    Team: ").append(getTeam().toString()).append("\n");
        builder.append("    Player: ").append(getPlayer().toString()).append("\n");
        builder.append("}");
        return builder.toString();
    }

    /**
     * Public main constructor of Goal object
     * @param game what goal happen, require object not null
     * @param team who did goal, require object not null
     * @param player who did goal, require object not null
     */
    public Goal(Game game, Team team, PlayerBase player){
        if(game == null)
            throw new IllegalArgumentException("game is null");
        if(team == null)
            throw new IllegalArgumentException("Team is null");
        if(player == null)
            throw new IllegalArgumentException("Player is null");

        this.Game = game;
        this.Team = team;
        this.Player = player;
    }
}
