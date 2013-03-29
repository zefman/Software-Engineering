
public class Team {

	private int collectedFood;
	private int antsRemaining;
	private int antsStarted;
	private int matchesWon;
	private int matchesLost;
	private int matchesDrawn;
	private String[] brain;
	private String teamName;
	
	public Team(String teamName, String[] brain) {
		collectedFood = 0;
		this.teamName = teamName;
		this.brain = brain;
	}
	
}
