package test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import soccerManager.Game;
import soccerManager.GameRunner;
import soccerManager.Team;
import soccerManager.player.Attacker;
import soccerManager.player.Defender;
import soccerManager.player.Goalkeeper;
import soccerManager.player.PlayerBase;
import test.soccerManager.player.AttackerTest;
import test.soccerManager.player.DefenderTest;
import test.soccerManager.player.GoalkeeperTest;
import test.soccerManager.player.PlayerBaseTest;

@RunWith(Suite.class)
@SuiteClasses({
    PlayerBaseTest.class,
    AttackerTest.class,
    DefenderTest.class,
    GoalkeeperTest.class
})
public class TestSuite {
    
    public static void simulateGames(){
        var rd = new Random(LocalDateTime.now().getNano());
        String[] names = {
            "Joe not exotic",
            "Cute as ducks",
            "Fedora the explorer",
            "Something",
            "Marcelo",
            "Definitely not an athlete",
            "Hoosier daddy",
            "Real name hidden",
            "Me for president",
            "Heart Ticker",
            "Gandalf",
            "Bad karma",
        };

        var teamCount = 100;
        var players = new ArrayList<PlayerBase>();
        for(int i = 0; i < teamCount*5; i++){
            var dt1 = LocalDate.of(rd.nextInt(1900,2000), rd.nextInt(1,12), rd.nextInt(1,29));
            var dt2 = LocalDate.of(rd.nextInt(1900,2000), rd.nextInt(1,12), rd.nextInt(1,29));
            var dt3 = LocalDate.of(rd.nextInt(1900,2000), rd.nextInt(1,12), rd.nextInt(1,29));
            players.add(new Attacker(names[rd.nextInt(names.length)], dt1, rd.nextInt(210), rd.nextInt(100), rd.nextInt(100)));
            players.add(new Defender(names[rd.nextInt(names.length)], dt2, rd.nextInt(210), rd.nextInt(100), rd.nextInt(100)));
            players.add(new Goalkeeper(names[rd.nextInt(names.length)], dt3, rd.nextInt(210), rd.nextInt(100)));
        }

        var teams = new ArrayList<Team>();
        for(int i = 0; i < teamCount; i++){
            var team = new Team(names[rd.nextInt(names.length)]);
            teams.add(team);

            for(int j = 0; j < 5; j++){
                var result = false;
                while(!result){
                    try{
                        var player = players.get(rd.nextInt(players.size()));
                        team.addPlayer(player);
                        players.remove(player);
                        result = true;
                    }catch(Exception ex){result=false;}
                }
            }
        }

        var games = new ArrayList<Game>();
        for(int i = 0; i < 500; i++){
            var team1 = teams.get(rd.nextInt(teamCount));
            var team2 = teams.get(rd.nextInt(teamCount));
            while(team1 == team2){
                team2 = teams.get(rd.nextInt(teamCount));
            }
            games.add(Game.createGame(team1, team2, "Kobrasol"));
        }


        for (Game game : games) {
            new GameRunner(game).run(10, 1);
        }
    }

}
