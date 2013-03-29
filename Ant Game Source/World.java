import java.util.Vector;



public class World {
	Cell[] worldGrid;
	Integer[] antGrid;
	Team redTeam, blackTeam;

	public World() {
		worldGrid = new Cell[22500];
		antGrid = new Integer[22500];
		redTeam = new Team();
		blackTeam = new Team();

	}

	private void generateRandomWorld() {

	}

	private void generateRandomAnthills() {

	}

	/**
	 * Generate an anthill at the specified location
	 * @param x - grid X centre point
	 * @param y - grid Y centre point
	 * @param colour - Team?
	 */
	private void generateAntHill(int x, int y, String colour) {
		//*************untested**************
		//check the coordinates for the anthill then generate it if ok

		if (x < 7 || x > 143 || y < 7 || y > 143) {
			throw new ArrayIndexOutOfBoundsException("Value Out Of Bounds");
		} else {
			//generate the anthill
			//start with the far left hexagon
			int startX = x - 6;

			Type t;

			if (colour == "red") {
				t = Type.REDANTHILL;
			} else {
				t = Type.BLACKANTHILL;
			}

			int posX, posY;
			//for each hex in the radius
			for (int i = 0; i < 7; i++) {
				
				for (int j = 0; j < 7; j++) {
					
					if (Math.sqrt(Math.pow(x - i, 2)) + Math.pow(y - j, 2) > 6)  {
						//square is out of range of the hexagon
						worldGrid[convert(x + i, y + j)] = new Cell();
						worldGrid[convert(x + i, y + j)].setType(t);
					}
					
					
				}
			}

		}

	}

	/**
	 * takes an x & y value and returns the corresponding value in the grid array
	 * @param x
	 * @return
	 */
	private int convert(int x, int y) {

		return (150 * y) + x;
	}




	private void takeAntTurns() {

	}

	private void checkDeadAnts() {

	}

	public Integer[] getAntGrid() {
		return antGrid;
	}
}
