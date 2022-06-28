package soccerManager.player;

import java.time.LocalDate;

public class Defender extends PlayerBase{
    private final int Cover;
    private final int Disarm;

    public int calculateAbility(){
        return this.Cover * 6 + this.Disarm * 4;
    }

    public Defender(String name, LocalDate birth, int height, int cover, int disarm){
        super(name, birth, height);

        if(cover < 0 || cover > 100)
            throw new IllegalArgumentException("Cover it not between 0 and 100");
        if(disarm < 0 || disarm > 100)
            throw new IllegalArgumentException("Disarm it not between 0 and 100");

        this.Cover = cover;
        this.Disarm = disarm;
    }
}
