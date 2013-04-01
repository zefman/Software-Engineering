/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author jozefmaxted
 */
public class World {
    Cell[] worldGrid;
    Integer[] antGrid;
    Team redTeam, blackTeam;

    public World(Team redTeam, Team blackTeam) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
	worldGrid = new Cell[22500];
	antGrid = new Integer[22500];
    }
    
    public World(Team redTeam, Team blackTeam, Path worldSave) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
	worldGrid = new Cell[22500];
	antGrid = new Integer[22500];
        
        //Get the world from the file
        Charset charset = Charset.defaultCharset();
        try (BufferedReader reader = Files.newBufferedReader(worldSave, charset)) {
            int line = 0;
            int cellCounter = 0;
            
            //Move the reader past the first two lines of the world file
            int newLine = 0;
            while (newLine < 2) {
                if (reader.read() == 10) {
                    System.out.println("Line Skipped");
                    newLine++;
                }
            }
            // Read the file into the cells
            while ((line = reader.read() ) != -1) {
                switch (line) {
                case 35:
                    worldGrid[cellCounter] = new Cell(Cell.Type.ROCKY);
                    System.out.print("Cell: " + cellCounter + " Rocky ");
                    cellCounter++;
                    break;
                case 46:
                    worldGrid[cellCounter] = new Cell(Cell.Type.CLEAR);
                    System.out.print("Cell:" + cellCounter + " Clear");
                    cellCounter++;
                    break;
                case 53:
                    worldGrid[cellCounter] = new Cell(Cell.Type.FOOD);
                    System.out.print("Cell:" + cellCounter + " contains food");
                    cellCounter++;
                    break;
                case 43:
                    worldGrid[cellCounter] = new Cell(Cell.Type.REDANTHILL);
                    System.out.print("Cell:" + cellCounter + " Red Ant Hill");
                    cellCounter++;
                    break;
                case 45:
                    worldGrid[cellCounter] = new Cell(Cell.Type.BLACKANTHILL);
                    System.out.print("Cell:" + cellCounter + " Black Ant Hill");
                    cellCounter++;
                    break;
                case 32:
                    //System.out.print("Space ignored");
                    break;
                case 10:
                    System.out.println();
                    break;
                default:
                    System.out.println("Character skipped **************************************************************** " + line);
                    break;
                }
                
            }
            
            System.out.println("How many cells " + cellCounter);
        } catch (IOException x) {
            //
        }
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
            /*
            int startX = x - 6;
            Type t;
            if (colour == "red") {
		t = Type.REDANTHILL;
            } else {
		t = Type.BLACKANTHILL;
            }
            */
            int posX, posY;
            //for each hex in the radius
            for (int i = 0; i < 7; i++) {
		
                for (int j = 0; j < 7; j++) {
					
                    if (Math.sqrt(Math.pow(x - i, 2)) + Math.pow(y - j, 2) > 6)  {
			//square is out of range of the hexagon
			worldGrid[convert(x + i, y + j)] = new Cell(Cell.Type.CLEAR);
			//worldGrid[convert(x + i, y + j)].setType(t);
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
