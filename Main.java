package mm_bracket_maker;
import java.io.IOException;

// Author: Kevin Land
// Based on data from 1985-2021 
// Source: https://apps.washingtonpost.com/sports/ 
// Source: https://bracketresearch.com/the-dna-of-a-national-championship-team/seeds-of-ncaa-tournament-champions/ 

public class Main{    
    // WINNING_CHANCE variables show the chance of a win versus another seed. (index = seed-1)
    // For example, WINNING_CHANCE_1[15] shows the how often seed 1 beats seed 16. 
    // The same seed, 1 vs 1, will always be 50/50 because it can't be calculated. 
    // Negative one indicates that there has never been a matchup with both of those seeds. 

    public static void main(String[] args){
        // All the teams. Seed is based on order of teams listed. (first listed is seed 1)
        String west[] = {"Gonzaga","Duke","Texas Tech","Arkansas","Uconn","Alabama","Michigan St."
                        ,"Boise St.","Memphis","Davidson","RUT/ND","New Mexico St.","Vermont"
                        ,"Montana St.","CS Fullerton","Georgia St."};
        String south[] = {"Arizona","Villinova","Tennessee","Illinois","Houston","Colorado St."
                        ,"Ohio St.","Seton Hall","TCU","Loyola Chicago","Michigan","UAB","Chattanooga"
                        ,"Longwood","Delaware","Wright"};
        String east[] = {"Kentucky","Baylor","Purdue","UCLA","Saint Mary's","Texas","Murray St."
                        ,"North Carolina","Marquette","San Francisco","Virginia Tech","IU","Akron"
                        ,"Yale","Saint Peter's","Norfolk St."};
        String midwest[] = {"Kansas","AUB","WIS","PROV","IOWA","LSU","USC","SDSU","CREIGH","MIAMI","IA ST"
                        ,"RICH","SDAKST","GATE","JAX ST","TX SO"};

        try{
            Bracket bracket = new Bracket();

            for(int i = 0; i < 16; i++){ // Goes through each team
                bracket.addTeam(new Team(west[i], i+1, Region.WEST));
                bracket.addTeam(new Team(south[i], i+1, Region.SOUTH));
                bracket.addTeam(new Team(east[i], i+1, Region.EAST));
                bracket.addTeam(new Team(midwest[i], i+1, Region.MIDWEST));
            }

            bracket.getWinner();
            bracket.finalRounds();
        }
        catch(IOException ioe){ // Catches IOException from Team
            System.out.println("Error in main.");
        }
    }
}