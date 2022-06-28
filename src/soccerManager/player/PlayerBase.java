package soccerManager.player;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import soccerManager.*;
import soccerManager.objectTracking.*;

public abstract class PlayerBase extends ObjectIdentity {
    private final int Height;
    private final String Name;
    private final LocalDate Birth;
    private final ArrayList<Goal> Goals;

    private int ShirtNumber;
    private Team CurrentTeam;

    public Team getTeam(){
        return this.CurrentTeam;
    }

    public void setCurrentTeam(Team newTeam){
        this.CurrentTeam = newTeam;
    }

    public int getShirtNumber(){
        return this.ShirtNumber;
    }

    /**
     * Set the player shirt number
     * @param newNumber new number of player's shirt, require equal or bigger than 1
     */
    public void setShirtNumber(int newNumber){
        if(newNumber <= 0)
            throw new IllegalArgumentException("New number is equal or smaller than zero");

        this.ShirtNumber = newNumber;
    }

    public int getHeight(){
        return this.Height;
    }

    public String getName(){
        return this.Name;
    }

    public LocalDate getBirth(){
        return this.Birth;
    }

    public int getAge(){
        int age = Period.between(getBirth(), LocalDate.now()).getYears();
        return age;
    }

    public ArrayList<Goal> getGoals(){
        return this.Goals;
    }

    /**
     * Get player goals by team
     * @param team the team object, require not null object
     * @return array of goals did in the team
     */
    public Goal[] getGoalsByTeam(Team team){
        if(team == null)
            throw new IllegalArgumentException("team is null");

        return (Goal[])this.Goals.stream().filter(g->g.getTeam() == team).toArray();
    }

    /**
     * Get player's goals by team GUID
     * @param guid of team you want to get player's goals, require not null or empty
     * @return return all goals of player did int the team
     */
    public Goal[] getGoalsByTeamGUID(String guid){
        if(guid == null || guid.isBlank())
            throw new IllegalArgumentException("guid is null or empty");

        return (Goal[])this.Goals.stream().filter(g->g.getTeam().getGUID().equals(guid)).toArray();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("{\n");
        builder.append("    GUID: ").append(getGUID()).append("\n");
        builder.append("    name: ").append(getName()).append("\n");
        builder.append("    birth: ").append(getBirth()).append("\n");
        builder.append("    age: ").append(getAge()).append("\n");
        builder.append("    height: ").append(getHeight()).append("\n");
        builder.append("    ability: ").append(calculateAbility()).append("\n");
        builder.append("}");
        return builder.toString();
    }

    /**
     * Used for calculate player ability based on his height and type
     * @return a value between 0 and 100, and this value can decide who wins
     */
    public abstract int calculateAbility();

    /**
     * Calculate the normalized player's height value
     * @param height a value between 0 and 210, values out of this range will throw exceptions
     * @return normalized height value, 0 is 0 and 210 is 100
     */
    private int calculateNormalizedHeight(int height){
        var mathContext = new MathContext(2, RoundingMode.HALF_EVEN);
        var min = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_EVEN);
        var max = BigDecimal.valueOf(210).setScale(2, RoundingMode.HALF_EVEN);
        var hundred = BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_EVEN);
        var heightBig = BigDecimal.valueOf(height).setScale(2, RoundingMode.HALF_EVEN);

        if(heightBig.compareTo(min) == -1)
            throw new IllegalArgumentException("Invalid height, is smaller than zero");
        if(heightBig.compareTo(max) == 1)
            throw new IllegalArgumentException("Invalid height, is bigger than 210");

        var result = (heightBig.subtract(min)).divide(max.subtract(min), mathContext).multiply(hundred, mathContext);

        if(result.compareTo(min) == -1)
            throw new IllegalArgumentException("Final height smaller than zero!");
        if(result.compareTo(hundred) == 1)
            throw new IllegalArgumentException("Final height higher than 100!");

        return result.intValue();
    }

    /**
     * Public main unique main constructor of PlayerBase
     * @param name is the player's full name, it cannot be null or empty
     * @param birth is when player birth, cannot be null and smaller than current date
     * @param height is the player's height and require a value between 0 and 210, these values are in centimeters
     */
    public PlayerBase(String name, LocalDate birth, int height){
        if(name == null || name.isEmpty())
            throw new IllegalArgumentException("Player name is null or empty!");
        if(birth == null)
            throw new IllegalArgumentException("Birth is null");
        if(birth.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Birth is higher than current date");

        this.Name = name;
        this.Birth = birth;
        this.CurrentTeam = null;
        this.Goals = new ArrayList<>();
        this.Height = calculateNormalizedHeight(height); //this function validate height
    }
}