package test.soccerManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import soccerManager.player.Attacker;
import soccerManager.player.Defender;
import soccerManager.player.Goalkeeper;
import soccerManager.Team;

public class TeamTest {

    @Test
    public void testTeamAbility(){
        var team = new Team("Cannot crash");

        team.addPlayer(new Attacker("asd", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Attacker("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100));

        team.addPlayer(new Defender("asd", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Defender("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100));

        team.addPlayer(new Goalkeeper("asd", LocalDate.parse("2000-01-01"), 180, 100));

        assertEquals(4944, team.calculateAbility());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxAttackers(){
        var team = new Team("Go crash");

        team.addPlayer(new Attacker("asd", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Attacker("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Attacker("v", LocalDate.parse("2000-01-01"), 180, 100, 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxDefender(){
        var team = new Team("Go crash");

        team.addPlayer(new Defender("asd", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Defender("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Defender("v", LocalDate.parse("2000-01-01"), 180, 100, 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxGoalkeeper(){
        var team = new Team("Go crash");

        team.addPlayer(new Goalkeeper("asd", LocalDate.parse("2000-01-01"), 180, 100));
        team.addPlayer(new Goalkeeper("dfg", LocalDate.parse("2000-01-01"), 180, 100));
    }


    @Test
    public void testMaxAttackers2(){
        var team = new Team("Cannot crash");

        team.addPlayer(new Attacker("asd", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Attacker("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100));
    }

    @Test
    public void testMaxDefender2(){
        var team = new Team("Cannot crash");

        team.addPlayer(new Defender("asd", LocalDate.parse("2000-01-01"), 180, 100, 100));
        team.addPlayer(new Defender("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100));
    }

    @Test
    public void testMaxGoalkeeper2(){
        var team = new Team("Cannot crash");

        team.addPlayer(new Goalkeeper("asd", LocalDate.parse("2000-01-01"), 180, 100));
    }



    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNotFoundPlayer(){
        var team = new Team("crash");

        team.addPlayer(new Defender("asd", LocalDate.parse("2000-01-01"), 180, 100, 100));

        var p = new Defender("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100);

        team.removePlayer(p);
    }

    @Test
    public void testRemovePlayer(){
        var team = new Team("crash");

        var p = new Defender("dfg", LocalDate.parse("2000-01-01"), 180, 100, 100);

        team.addPlayer(p);
        team.removePlayer(p);
    }
}
