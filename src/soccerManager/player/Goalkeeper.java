package soccerManager.player;

import java.time.LocalDate;

public class Goalkeeper extends PlayerBase {
    private final int Reflexes;

    public int getReflexes(){
        return this.Reflexes;
    }

    public int calculateAbility(){
        return getHeight() * 4 + this.Reflexes * 6;
    }

    public Goalkeeper(String name, LocalDate birth, int height, int reflexes){
        super(name, birth, height);

        if(reflexes < 0 || reflexes > 100)
            throw new IllegalArgumentException("Reflexes it not between 0 and 100");

        this.Reflexes = reflexes;
    }
}
