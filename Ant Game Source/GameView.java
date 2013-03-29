
public class GameView {

	//	Cell[] worldGrid;
	/**
	 * Render/ draw the world to the players
	 * @param worldGrid - the array of the world to be drawn
	 */
	public void render(Cell[] worldGrid) {

		//render the grid to console
		renderGrid(worldGrid);



	}

	public void renderGrid(Cell[] worldGrid) {
		//iterate
		for (int i = 0; i < 22499; i++) {
			//make a new line if edge of grid
			if ((i + 1) % 150 == 0) System.out.println();
			//get the type of cell and print it
			switch (worldGrid[i].getType()) {
			case ROCKY:
				System.out.print("#");
				break;
			case CLEAR :
				System.out.print(".");
				break;
			case REDANTHILL :
				System.out.print("+");
				break;
			case BLACKANTHILL :
				System.out.print("-");
				break;
			case REDANT :
				System.out.print("r");
				break;
			case BLACKANT :
				System.out.print("b");
				break;
			case REDWITHFOOD : 
				System.out.print("R");
				break;
			case BLACKWITHFOOD : 
				System.out.print("B");
				break;
			}
		}
	}


	/*
	 * ?
	 */
	public void drawTeamStats() {

	}
}
