package mm_bracket_maker;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class versus all the teams to create a bracket. 
 */
public class Bracket{
    // CHAMPION_CHANCE shows the percent of all championships each seed has. (seed = index-1).
    // Only seeds 1-4 and 6-8 have won. 
    // Chances: 0.64, 0.14, 0.11, 0.03, 0.00, 0.03, 0.03, 0.02.
    private final double CHAMPION_CHANCE[] = {0.64, 0.78, 0.89, 0.92, 0.00, 0.95, 0.98, 0.100};

    private List<Team> south; // South teams
    private List<Team> west; // West teams
    private List<Team> midwest; // Midwest teams
    private List<Team> east; // East teams
    private Team winner; // The overall winner of the bracket

    public Bracket(){
        south = new ArrayList<>();
        west = new ArrayList<>();
        midwest = new ArrayList<>();
        east = new ArrayList<>();
    }

    // Getters
    public List<Team> getEast() {
        return east;
    }
    public List<Team> getMidwest() {
        return midwest;
    }
    public List<Team> getSouth() {
        return south;
    }
    public List<Team> getWest() {
        return west;
    }

    public Team getWinner(){
        if(winner == null && east.size() + west.size() + south.size() + midwest.size() == 64){
            Random rand = new Random();
            double chance = ((double)rand.nextInt(100)+1)/100; //Gets random number (0.01-1.00)
            int seed = 0;

            // Uses the Champion Chances and the random number to get a winning seed
            if(chance <= CHAMPION_CHANCE[0]){
                seed = 1;
            }
            else if(CHAMPION_CHANCE[0] < chance && chance <= CHAMPION_CHANCE[1]){
                seed = 2;
            }
            else if(CHAMPION_CHANCE[1] < chance && chance <= CHAMPION_CHANCE[2]){
                seed = 3;
            }
            else if(CHAMPION_CHANCE[2] < chance && chance <= CHAMPION_CHANCE[3]){
                seed = 4;
            }
            else if(CHAMPION_CHANCE[3] < chance && chance <= CHAMPION_CHANCE[5]){
                seed = 5;
            }
            else if(CHAMPION_CHANCE[5] < chance && chance <= CHAMPION_CHANCE[6]){
                seed = 6;
            }
            else{
                seed = 7;
            }

            // Randomly selects a team from the chosen seed. 
            chance = rand.nextInt(3); // Gets int 0-3
            if(chance == 0){
                winner = south.get(seed);
            }
            else if(chance == 1){
                winner = east.get(seed);
            }
            else if(chance == 2){
                winner = west.get(seed);
            }
            else{
                winner = midwest.get(seed);
            }
        }

        // Sets that team as the champion
        winner.setChampion(true);
        return winner;
    }

    // Adds a team to the proper list. 
    public void addTeam(Team team){
        if(team.getRegion() == Region.EAST){
            east.add(team);
        }
        else if(team.getRegion() == Region.SOUTH){
            south.add(team);
        }
        else if(team.getRegion() == Region.WEST){
            west.add(team);
        }
        else if(team.getRegion() == Region.MIDWEST){
            midwest.add(team);
        }
        else{
            System.out.println("Error adding team to a region.");
        }
    }

    // Puts all teams in a division against eachother to find division champion. 
    public Team playDivision(List<Team> region){
        Team firstRound[] = new Team[8]; // Array for first round losers
        int order[] = {1, 8, 6, 4, 3, 5, 7, 2}; // Order that the teams compete in. (ex. 1 vs 8 and 6 vs 4)
        Collections.sort(region); // Sorts teams by seed

        for(int i = 0; i < region.size()/2; i++){ 
            Team topSeed = region.get(i); // first position to the middle
            Team lowSeed = region.get(region.size()-1-i); // last position to the middle
            topSeed.setOrder(order[i]-1); // Order of the matchup
            lowSeed.setOrder(order[i]-1); // Order of the mathcup
            firstRound[order[i]-1] = topSeed.matchup(lowSeed); // Adds loser to firstRound
        }

        // Shows the first round games.
        Collections.sort(region, new OrderComparator());
        System.out.println("\nAll Match Ups: " + region);

        // removes losers from competing teams.
        for(Team team : firstRound){
            region.remove(team);
        }

        // Shows winners of the first round games
        Collections.sort(region, new OrderComparator());
        System.out.println("\nFirst Round Winners: " + region);

        // Plays rounds until there is a winner.
        for(int i = 1; i < 4; i++){
            playRound(region);
            System.out.println("Round " + i + " Winners: " + region);
        }

        return region.get(0); // Winner of the division
    }

    // Competes teams against each other for one round.
    public void playRound(List<Team> region){
        Team round[] = new Team[region.size()/2]; // Creates an array for round losers. 

        for(int i = 0; i < region.size()/2; i++){
            Team topSeed = region.get(i*2); // Gets even teams
            Team lowSeed = region.get(i*2+1); // Gets odd teams

            round[i] = topSeed.matchup(lowSeed); // Matches them up
        }

        // Removes the losing teams from the teams in region.
        for(Team team : round){
            region.remove(team);
        }

        // Puts the teams back in their competing order. 
        Collections.sort(region, new OrderComparator()); 
    }

    // Puts all four division champions against eachother to get overall champion.
    public Team finalRounds(){
        List<Team> finalists = new ArrayList<>(); // All division champions
        Team eastWinner = playDivision(east); // East Champion
        Team southWinner = playDivision(south); // South Champion
        Team westWinner = playDivision(west); // West Champion
        Team midwestWinner = playDivision(midwest); // Midwest Champion

        // Adds champions to finalists
        finalists.add(eastWinner);
        finalists.add(westWinner);
        finalists.add(southWinner);
        finalists.add(midwestWinner);

        System.out.println("\nFinal Four: " + finalists);

        // Gets final two
        finalists.remove(westWinner.matchup(eastWinner));
        finalists.remove(southWinner.matchup(midwestWinner));

        System.out.println("Final Two: " + finalists);

        // Gets winner
        finalists.remove(finalists.get(0).matchup(finalists.get(1)));
        System.out.println("Winner: " + winner);
        return winner;
    }
}