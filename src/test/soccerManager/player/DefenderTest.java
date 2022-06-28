package test.soccerManager.player;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import soccerManager.player.*;

public class DefenderTest {

    @Test
    public void testAbility(){
        // Cover * 6 + Disarm * 4
        Defender player = new Defender("Bruno", LocalDate.parse("2000-01-01"), 180, 45, 39);
        assertEquals(426, player.calculateAbility());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCoverException1(){
        new Defender("Bruno", LocalDate.parse("2000-01-01"), 180, -55, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCoverException2(){
        new Defender("Bruno", LocalDate.parse("2000-01-01"), 180, 654, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisarmException1(){
        new Defender("Bruno", LocalDate.parse("2000-01-01"), 180, 65, -50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisarmException2(){
        new Defender("Bruno", LocalDate.parse("2000-01-01"), 180, 65, 500);
    }


}
