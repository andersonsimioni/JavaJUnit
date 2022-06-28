package soccerManager;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import soccerManager.player.Defender;
import soccerManager.player.Goalkeeper;
import soccerManager.player.PlayerBase;

public class GameRunner {
    private final Game Game;
    private final Random RandomGenerator;

    private void renderWelcome(){
        String homeName = this.Game.getHomeTeam().getName();
        String visitorName = this.Game.getVisitorTeam().getName();
        int homeAbility = this.Game.getHomeTeam().calculateAbility();
        int visitorAbility = this.Game.getVisitorTeam().calculateAbility();

        System.out.println("WELCOME TO GAME !!");
        System.out.println(MessageFormat.format("(Home) {0} -VS- {1} (Visitor)", homeName, visitorName));
        System.out.println(MessageFormat.format("Ability: (Home) {0} | {1} (Visitor)", homeAbility, visitorAbility));
    }

    private void renderScoreboard(){
        int homeGoals = this.Game.getGoalsByTeam(this.Game.getHomeTeam());
        int visitorGoals = this.Game.getGoalsByTeam(this.Game.getVisitorTeam());

        System.out.println("## Scoreboard ##");
        System.out.println(MessageFormat.format("{0} HOME     |     {1} VISITOR", homeGoals, visitorGoals));
    }

    private void renderFinalResult(){
        Team winner = this.Game.getWinnerTeam();
        if(winner==null)
            System.out.println("The game has break even!!!");
        else
            System.out.println(MessageFormat.format("{0} is the winner !!", winner.getName()));
    }

    private void renderGoalEvent(Team team, PlayerBase player){
        System.out.println(MessageFormat.format("GOAL of {0} by player {1}!!", team.getName(), player.getName()));
    }

    private PlayerBase sortPlayerToGoal(Team team){
        PlayerBase player = null;

        while(player == null || player.getClass().isAssignableFrom(Goalkeeper.class))
            player = team.getPlayers().get(this.RandomGenerator.nextInt(team.getPlayers().size()));

        return player;
    }

    /**
     * Get teams abilities sum,
     * calculate percent of each team ability based on sum,
     * sort double until find winner,
     * example, home=70%  visitor=40%
     * >home will win in theses cases
     * >>home: 0.6, visitor: 0.3
     * >>home: 0.65, visitor: 0.2
     * 
     * >nobody win in these cases
     * >>home: 0.6, visitor: 0.4
     * >>home: 0.7, visitor: 0.5
     * 
     * >visitor will win in theses cases
     * >>home: 0.3, visitor: 0.4
     * >>home: 0.1, visitor: 0.25
     * @return
     */
    private Team sortTeamToGoal(){
        int homeAdvantage = 100;
        int homeAbility = this.Game.getHomeTeam().calculateAbility() + homeAdvantage;
        int visitorAbility = this.Game.getVisitorTeam().calculateAbility();

        float sumChances = homeAbility + visitorAbility;
        float homePercent = homeAbility / sumChances;
        float visitorPercent = visitorAbility / sumChances;

        boolean homeGoal = false, visitorGoal = false;
        while(homeGoal == visitorGoal){
            homeGoal = this.RandomGenerator.nextDouble() <= homePercent; //  ex 70% 
            visitorGoal = this.RandomGenerator.nextDouble() <= visitorPercent; // ex 40%
        }

        return homeGoal ? this.Game.getHomeTeam() : this.Game.getVisitorTeam();
    }

    public void run(int maximumGoals, int stepDelay){
        renderWelcome();
        this.Game.start();

        for(int i = 0; i < maximumGoals; i++){
            try
            {
                Team goalTeam = sortTeamToGoal();
                PlayerBase goalPlayer = sortPlayerToGoal(goalTeam);
                this.Game.addGoal(new Goal(this.Game, goalTeam, goalPlayer));
                renderGoalEvent(goalTeam, goalPlayer);
                renderScoreboard();
                System.out.println("\n");

                Thread.sleep(stepDelay);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }

        this.Game.end();
        renderFinalResult();
    }

    public GameRunner(Game gameToRun){
        if(gameToRun == null)
            throw new IllegalArgumentException("game is null");
        
        this.Game = gameToRun;
        this.RandomGenerator = new Random(LocalDateTime.now().getNano());
    }
}
