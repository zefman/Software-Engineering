
public class Team {

	private int collectedFood;
	private int antsRemaining;
	private int antsStarted;
	private int matchesWon;
	private int matchesLost;
	private int matchesDrawn;
	private String[] brain;
	private String teamName;
	
	private Team() {
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

