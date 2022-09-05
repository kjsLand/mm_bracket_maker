package mm_bracket_maker;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * This is a helper class for Bracket which allows for teams to be compared. 
 * Constructor throws IOException because it uses BufferedReader. 
 */
public class Team implements Comparable<Team>{
    private int seed; // Seed of team
    private double[] winning_chance; // The chances of success from WinningChances.txt
    private String name; // Name of the team
    private Region region; // Region of the team
    private boolean champion; // True if user picks this team as the overall winner, false otherwise. 
    private int order; // A number used to keep track of the matchups of each round.

    public Team(String name, int seed, Region region) throws IOException{
        this.name = name;
        this.seed = seed;
        this.region = region;
        champion = false;

        // Reads through file
        FileReader fr = new FileReader("WinningChances.txt");
        BufferedReader reader = new BufferedReader(fr);
        String line = reader.readLine();

        int lineNum = seed;
        while(line != null && lineNum > 1){
            lineNum--;
            line = reader.readLine();
        }
        reader.close();

        // Gets the winning chances of the current seed versus all other seeds. 
        String chances[] = line.split(", ");
        winning_chance = new double[16];
        for(int i = 0; i < chances.length; i++){
            winning_chance[i] = Double.parseDouble(chances[i]);
        }
    }

    // Getters
    public String getName() {
        return name;
    }
    public int getSeed() {
        return seed;
    }
    public double[] getWinning_chance() {
        return winning_chance;
    }
    public Region getRegion() {
        return region;
    }
    public boolean isChampion(){
        return champion;
    }
    public int getOrder() {
        return order;
    }

    // Setters
    public void setOrder(int order){
        this.order = order;
    }
    public void setChampion(boolean champion) {
        this.champion = champion;
    }

    /**
     * Puts two teams up against eachother.
     * @param opponent opposing team
     * @return loser of the matchup.
     */
    public Team matchup(Team opponent){
        if(this.isChampion()){ // If this team is overall winner
            return opponent;
        }
        else if(opponent.isChampion()){ // If other team is overall winner
            return this;
        }
        else{ // If neither team is the overall winner.
            Random rand = new Random();
            double chance = ((double)rand.nextInt(100)+1)/100;

            // Uses random number and winning_chance[] to find a winner
            if(this.getWinning_chance()[opponent.getSeed()-1] >= chance){
                return opponent;
            }
            else{
                return this;
            }
        }
    }

    // To string for Teams
    // Shows the teams name and seed
    @Override
    public String toString() {
        return String.format("%s: %d", name, seed);
    }

    // Compares the teams by their seed. 
    @Override
    public int compareTo(Team o) {
        return this.getSeed() - o.getSeed();
    }
}