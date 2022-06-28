package test.soccerManager.player;

import org.junit.*;

import soccerManager.player.Attacker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

public class AttackerTest {

    @Test
    public void testAbility(){
        //Speed * 4 + Technique * 6;
        Attacker player = new Attacker("Roberto", LocalDate.parse("2000-01-01"), 180, 55, 50);
        assertEquals(520, player.calculateAbility());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpeedException1(){
        new Attacker("Roberto", LocalDate.parse("2000-01-01"), 180, -55, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpeedException2(){
        new Attacker("Roberto", LocalDate.parse("2000-01-01"), 180, 654, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTechniqueException1(){
        new Attacker("Roberto", LocalDate.parse("2000-01-01"), 180, 65, -50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTechniqueException2(){
        new Attacker("Roberto", LocalDate.parse("2000-01-01"), 180, 65, 500);
    }

}
