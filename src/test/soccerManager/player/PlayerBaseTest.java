package test.soccerManager.player;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import soccerManager.player.*;
import soccerManager.player.Goalkeeper;

public class PlayerBaseTest {
    @Test(expected = IllegalArgumentException.class)
    public void testNullNameException(){
        new Goalkeeper(null, LocalDate.parse("2000-01-01"), 180, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyNameException(){
        new Goalkeeper("", LocalDate.parse("2000-01-01"), 180, 50);
    }

    @Test()
    public void testName(){
        PlayerBase player = new Goalkeeper("Marcelo", LocalDate.parse("2000-01-01"), 180, 50);
        assertEquals("Marcelo", player.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullBirthException(){
        new Goalkeeper("Marcelo", null, 180, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthRangeException(){
        new Goalkeeper("Marcelo", LocalDate.parse("2100-01-01"), 180, 50);
    }

    @Test()
    public void testBirth(){
        PlayerBase player = new Goalkeeper("Marcelo", LocalDate.parse("2000-01-01"), 180, 50);
        assertEquals("2000-01-01", player.getBirth().toString());
    }

    @Test
    public void testAge(){
        // WARNING !!
        // THE AGE WILL CHANGE EVERY YEAR!
        // THE ORIGINAL YEAR IS 2022
        PlayerBase player = new Goalkeeper("Marcelo", LocalDate.parse("2000-01-01"), 180, 50);
        assertEquals(22, player.getAge());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHeightException(){
        new Goalkeeper("Marcelo", LocalDate.parse("2000-01-01"), -50, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHeightException2(){
        new Goalkeeper("Marcelo", LocalDate.parse("2000-01-01"), 250, 50);
    }

    @Test
    public void testHeight(){
        for(int i = 0; i <= 210; i++){
            PlayerBase player = new Goalkeeper("Marcelo", LocalDate.parse("2000-01-01"), i, 50);
            assertTrue("Player height out of range", player.getHeight() >= 0 && player.getHeight() <= 100);
        }

        PlayerBase player2 = new Goalkeeper("Marcelo", LocalDate.parse("2000-01-01"), 105, 50);
        assertEquals(50, player2.getHeight());
    }
}
