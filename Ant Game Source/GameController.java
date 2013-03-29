
public class GameController {
	public static int currentRound;
	public static int gameSpeed;
	private World world;
	boolean gameOver;
	
	public GameController() {
		currentRound = 0;
		gameSpeed = 1;
		world = new World();
		gameOver = false;
	}
	
	/**
	 * Main Game loop
	 */
	public void runGame() {

		/**
		 * Main Game logic loop
		 */
		while (!gameOver) {
			
		}
	}
	
	public void updateTeams() {
		
	}
	
	public void nextTurn() {
		
	}
	
	public void updateGameView(Integer[] worldGrid, Integer[] antGrid) {
		
	}
	
	public void updateTeamStats(int collectedFood, int antsRemaining) {
		
	}
	
	public void saveGame() {
		
	}
	
	public void determineWinner() {
		
	}
	
	public void loadGameView() {
		
	}
	
	public void loadGameResultsView() {
		
	}
}
