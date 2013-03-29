
public class MainMenuController {
	GameController gameController;
	
	public MainMenuController() {
		//setup the game controller 
		startNewGame();
		
	}
	
	private void startNewGame() {
		gameController = new GameController();
	}
	
	private void startNewTournament() {
		
	}
	
	private void exitGame() {
		System.exit(-1);
	}
	
	private void openBrainEditor() {
		
	}
	
	private void openWorldEditor() {
		
	}
	
	private void loadMainMenuView() {
		
	}
	
	public static void main(String[] args) {
		MainMenuController c = new MainMenuController();
	}
	
}
