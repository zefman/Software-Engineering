/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.File;
import java.util.List;

/**
 *
 * @author jozefmaxted
 */
public class Team {
        private int collectedFood;
	private int antsRemaining;
	private int antsStarted;
	private int matchesWon;
	private int matchesLost;
	private int matchesDrawn;
	private List<String> brain;
	private String teamName;
        private File profile;
	
        //Constructor without profile file
	public Team(String teamName) {
            //Initialise variables
            this.teamName = teamName;
            this.profile = null;
            
            collectedFood=0;
            antsStarted=49;
            antsRemaining=49;
	}
        //Constructor with profile file
        public Team(String teamName, File profile) {
            //Initialise variables
            this.teamName = teamName;
            this.profile = profile;
            
            collectedFood=0;
            antsStarted=49;
            antsRemaining=49;
	}
	
	public void decreaseAnts() {
            antsRemaining--;
	}
	
	public void increaseCollectedFood() {
            collectedFood++;
	}
	
	public void updateMatchStats(boolean win, boolean draw) {
		
	}
	
	public int[] getMatchStats() {
            int[] matchStats = new int[3];
            matchStats[0] = matchesWon;
            matchStats[1] = matchesLost;
            matchStats[2] = matchesDrawn;
            return matchStats;
	}
        
        public void setBrain(List<String> brain) {
            this.brain = brain;
        }
	
	public String getName(){
            return teamName;
	}
	
	public int getAntsRemaining() {
            return antsRemaining;
	}
	
	public int getMatchesWon() {
            return matchesWon;
	}
	
	public int getMatchesLost() {
            return matchesLost;
	}
	
	public int getMatchesDrawn() {
            return matchesDrawn;
	}

	public int getCollectedFood() {
            return collectedFood;
	}
}
