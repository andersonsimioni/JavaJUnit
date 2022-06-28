package soccerManager;

import java.time.LocalDate;
import java.util.ArrayList;

import soccerManager.objectTracking.*;

public class Game extends ObjectIdentity {
    public enum GameStatus{
        NotStarted,
        Running,
        Paused,
        Finish
    }

    private final Team HomeTeam;
    private final Team VisitorTeam;
    private final LocalDate Date;
    private final String Location;
    private final ArrayList<Goal> Goals;
    
    private GameStatus Status;
    
    public String getLocation(){
        return this.Location;
    }

    public Team getHomeTeam(){
        return this.HomeTeam;
    }

    public Team getVisitorTeam(){
        return this.VisitorTeam;
    }

    public Team getWinnerTeam(){
        if(this.Status != GameStatus.Finish)
            return null;

        int homeGoals = getGoalsByTeam(this.HomeTeam);
        int visitorGoals = getGoalsByTeam(this.VisitorTeam);

        return homeGoals == visitorGoals ? null : (homeGoals > visitorGoals ? this.HomeTeam : this.VisitorTeam);
    }

    public ArrayList<Goal> getGoals(){
        return this.Goals;
    }

    public LocalDate getDate(){
        return this.Date;
    }
    
    public boolean isHomeTeam(Team team){
        return this.HomeTeam == team;
    }
    
    /**
     * Start the game,
     * require game status equal not started!
     */
    public void start(){
        if(this.Status == GameStatus.NotStarted)
            this.Status = GameStatus.Running;
        else
            throw new IllegalStateException("Game already started!");
    }

    /**
     * Set game status to paused,
     * require game status running
     */
    public void pause(){
        if(this.Status == GameStatus.Running)
            this.Status = GameStatus.Paused;
        else
            throw new IllegalStateException("Game is not running..");
    }

    /**
     * End the game,
     * require game status running or paused
     */
    public void end(){
        if(this.Status == GameStatus.Running || this.Status == GameStatus.Paused)
            this.Status = GameStatus.Finish;
        else
            throw new IllegalStateException("Game is not running or paused..");
    }

    /**
     * Add goal to game
     * @param newGoal to add to game
     */
    public void addGoal(Goal newGoal){
        if(this.Status != GameStatus.Running)
            throw new IllegalStateException("Game is not running!");

        this.Goals.add(newGoal);
    }

    /**
     * Calculate the sum of goals of the team in this game
     * @param team who want to calculate goals
     * @return sum of goals of the team in this game
    */
    public int getGoalsByTeam(Team team){
        return (int)this.Goals.stream().filter(g->g.getTeam() == team).count();
    }

    /**
     * Calculate team result points,
     * the game status need be finished
     * @param team who want get result
     * @return 3 if win, 1 if no have winner and 0 if lost or game not  finished
     */
    public int getResultPointsByTeam(Team team) {
        if(this.Status != GameStatus.Finish)
            return 0;
        
        Team enemy = this.HomeTeam == team ? this.VisitorTeam : this.HomeTeam;
        int thisTeamGoals = getGoalsByTeam(team);
        int enemyTeamGoals = getGoalsByTeam(enemy);

        return thisTeamGoals == enemyTeamGoals ? 1 : (thisTeamGoals > enemyTeamGoals ? 3 : 0);
    }

    /**
     * Create a new game using current date
     * @param team1 first team, require not null object
     * @param team2 second team, require not null object
     * @param location where game happen, example: Kobrasol, require not null or empty
     * @return
     */
    public static Game createGame(Team homeTeam, Team visitorTeam, String location){
        return new Game(homeTeam, visitorTeam, location, LocalDate.now());
    }

    /**
     * Create a new game using custom date
     * @param team1 first team, require not null object
     * @param team2 second team, require not null object
     * @param location location where game happen, example: Kobrasol, require not null or empty
     * @param date when game happen, require not null object
     * @return
     */
    public static Game createGame(Team homeTeam, Team visitorTeam, String location, LocalDate date){
        return new Game(homeTeam, visitorTeam, location, date);
    }
    
    /**
     * Private main constructor of Game object, 
     * this constructor is private because we have
     * to static functions to create games with custom date and current date
     * @param team1 first team, require not null object
     * @param team2 second team, require not null object
     * @param location where game happen, example: Kobrasol, require not null or empty
     * @param date when game happen, require not null object
     */
    private Game(Team homeTeam, Team visitorTeam, String location, LocalDate date){
        if(homeTeam == null)
            throw new IllegalArgumentException("Home team is null");
        if(visitorTeam == null)
            throw new IllegalArgumentException("Visitor team is null");
        if(location == null || location.isEmpty()) 
            throw new IllegalArgumentException("Location is null or empty");
        if(date == null)
            throw new IllegalArgumentException("Date is null");

        this.Date = date;
        this.HomeTeam = homeTeam;
        this.VisitorTeam = visitorTeam;
        this.Location = location;
        this.Goals = new ArrayList<>();
        this.Status = GameStatus.NotStarted;
    }
}
