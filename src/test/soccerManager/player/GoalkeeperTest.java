package test.soccerManager.player;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import soccerManager.Goal;
import soccerManager.player.*;
import soccerManager.player.Goalkeeper;


public class GoalkeeperTest {

    @Test
    public void testAbility(){
        // Height * 4 + Reflexes * 6;

        PlayerBase player = new Goalkeeper("Bruno", LocalDate.parse("2000-01-01"), 180, 43);
        assertEquals(602, player.calculateAbility());

        PlayerBase player2 = new Goalkeeper("Bruno 2", LocalDate.parse("2000-01-01"), 94, 65);
        assertEquals(570, player2.calculateAbility());

        //player3 computed height is 4.7619 but HALF_EVEN round to 4
        // HALF_EVEN
        // 3.2 -> 3
        // 3.4 -> 3
        // 3.5 -> 4
        // 4.5 -> 4
        // 5.5 -> 6
        PlayerBase player3 = new Goalkeeper("Bruno 3", LocalDate.parse("2000-01-01"), 10, 14);
        assertEquals(100, player3.calculateAbility());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReflexesException1(){
        new Goalkeeper("Bruno", LocalDate.parse("2000-01-01"), 180, -645);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReflexesException2(){
        new Goalkeeper("Bruno", LocalDate.parse("2000-01-01"), 180, 654);
    }

    @Test
    public void testReflexes(){
        Goalkeeper player = new Goalkeeper("Bruno", LocalDate.parse("2000-01-01"), 180, 43);
        assertEquals(43 , player.getReflexes());
    }

}
