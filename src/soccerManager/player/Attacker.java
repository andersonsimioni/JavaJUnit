package soccerManager.player;

import java.time.LocalDate;

public class Attacker extends PlayerBase {
    private final int Speed;
    private final int Technique;

    public int calculateAbility(){
        return this.Speed * 4 + this.Technique * 6;
    }
    
    public Attacker(String name, LocalDate birth, int height, int speed, int technique){
        super(name, birth, height);
        if(speed < 0 || speed > 100)
            throw new IllegalArgumentException("Speed it not between 0 and 100");
        if(technique < 0 || technique > 100)
            throw new IllegalArgumentException("Speed it not between 0 and 100");
        
        this.Speed = speed;
        this.Technique = technique;
    }
}
