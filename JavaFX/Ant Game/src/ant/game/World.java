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
	worldGrid = new Cell[16900];
	antGrid = new Integer[16900];
        
        //Initiallise all the cells
        for (int i = 0; i < worldGrid.length; i++) {
            worldGrid[i] = new Cell(Cell.Type.CLEAR);
        }
        
        //Set the outside cells to rocky
        for (int i=0; i < 130; i++) {
            //Sets the top line and bottom line
            worldGrid[i].setType(Cell.Type.ROCKY);
            worldGrid[worldGrid.length-i-1].setType(Cell.Type.ROCKY);
            worldGrid[i*130].setType(Cell.Type.ROCKY);
            worldGrid[i*130+130-1].setType(Cell.Type.ROCKY);
            worldGrid[16899].setType(Cell.Type.ROCKY);
        }
        
        
        System.out.println("Top set to rocky");
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
                    cellCounter++;
                    break;
                case 46:
                    worldGrid[cellCounter] = new Cell(Cell.Type.CLEAR);
                    cellCounter++;
                    break;
                case 53:
                    worldGrid[cellCounter] = new Cell(Cell.Type.FOOD);
                    cellCounter++;
                    break;
                case 43:
                    worldGrid[cellCounter] = new Cell(Cell.Type.REDANTHILL);
                    cellCounter++;
                    break;
                case 45:
                    worldGrid[cellCounter] = new Cell(Cell.Type.BLACKANTHILL);
                    cellCounter++;
                    break;
                case 32:
                    //System.out.print("Space ignored");
                    break;
                case 10:
                    //NEw Line
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
     
    public void generateFoodBlocks(int x, int y) {
        //First bottom row
        for (int i = 0; i < 4; i++) {
            worldGrid[x*130+y+i].setType(Cell.Type.FOOD);
            // Give five bits of food to the cell
            for (int j = 0; j < 5; j++) {
                worldGrid[x*130+y].giveFood();
            }
        }
        // Next two rows inset one to the right
        for (int i = 0; i < 4; i++) {
            worldGrid[(x+1)*130+y+i+1].setType(Cell.Type.FOOD);
            worldGrid[(x+2)*130+y+i+1].setType(Cell.Type.FOOD);
            // Give five bits of food to the cell
            for (int j = 0; j < 5; j++) {
                worldGrid[(x+1)*130+y+1].giveFood();
                worldGrid[(x+2)*130+y+1].giveFood();
            }
        }
        // Final row inset 2 to the right
        for (int i = 0; i < 4; i++) {
            worldGrid[(x+3)*130+y+i+2].setType(Cell.Type.FOOD);
            // Give five bits of food to the cell
            for (int j = 0; j < 5; j++) {
                worldGrid[(x+3)*130+y+2].giveFood();
            }
        }
    }
    
    public void generateAntHill(int x, int y, String colour) {
        //Starting from the cell furthest to the left do one whole line
        for (int i = 0; i < 11; i++) {
            if (colour.equals("red")) {
                worldGrid[x*130+y+i].setType(Cell.Type.REDANTHILL);
            } else {
                worldGrid[x*130+y+i].setType(Cell.Type.BLACKANTHILL);
            }
        }
        
        // Next change the two rows above and below the centre
        for (int i = 0; i < 10; i++) {
            if (colour.equals("red")) {
                worldGrid[(x+1)*130+y+1+i].setType(Cell.Type.REDANTHILL);
                worldGrid[(x-1)*130+y+1+i].setType(Cell.Type.REDANTHILL);
            } else {
                worldGrid[(x+1)*130+y+1+i].setType(Cell.Type.BLACKANTHILL);
                worldGrid[(x-1)*130+y+1+i].setType(Cell.Type.BLACKANTHILL);
            }
        }
        
        // Next rows inset by one on the right
        for (int i = 0; i < 9; i++) {
            if (colour.equals("red")) {
                worldGrid[(x+2)*130+y+1+i].setType(Cell.Type.REDANTHILL);
                worldGrid[(x-2)*130+y+1+i].setType(Cell.Type.REDANTHILL);
            } else {
                worldGrid[(x+2)*130+y+1+i].setType(Cell.Type.BLACKANTHILL);
                worldGrid[(x-2)*130+y+1+i].setType(Cell.Type.BLACKANTHILL);
            }
        }
        
        // Next Row inset by one more on the left none on the right
        for (int i = 0; i < 8; i++) {
            if (colour.equals("red")) {
                worldGrid[(x+3)*130+y+2+i].setType(Cell.Type.REDANTHILL);
                worldGrid[(x-3)*130+y+2+i].setType(Cell.Type.REDANTHILL);
            } else {
                worldGrid[(x+3)*130+y+2+i].setType(Cell.Type.BLACKANTHILL);
                worldGrid[(x-3)*130+y+2+i].setType(Cell.Type.BLACKANTHILL);
            }
        }
        
        // Next Row inset by one more on the right and none on the left
        for (int i = 0; i < 7; i++) {
            if (colour.equals("red")) {
                worldGrid[(x+4)*130+y+2+i].setType(Cell.Type.REDANTHILL);
                worldGrid[(x-4)*130+y+2+i].setType(Cell.Type.REDANTHILL);
            } else {
                worldGrid[(x+4)*130+y+2+i].setType(Cell.Type.BLACKANTHILL);
                worldGrid[(x-4)*130+y+2+i].setType(Cell.Type.BLACKANTHILL);
            }
        }
        
        // Next Row inset by one more on the left and none on the right
        for (int i = 0; i < 6; i++) {
            if (colour.equals("red")) {
                worldGrid[(x+5)*130+y+3+i].setType(Cell.Type.REDANTHILL);
                worldGrid[(x-5)*130+y+3+i].setType(Cell.Type.REDANTHILL);
            } else {
                worldGrid[(x+5)*130+y+3+i].setType(Cell.Type.BLACKANTHILL);
                worldGrid[(x-5)*130+y+3+i].setType(Cell.Type.BLACKANTHILL);
            }
        }
    }

    /**
     * Generate an anthill at the specified location
     * @param x - grid X centre point
     * @param y - grid Y centre point
     * @param colour - Team?
     */
    private void generateAntHill2(int x, int y, String colour) {
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
    
    public void test() {
        System.out.println("udshfudshfus");
    }
    
    public void setTeams(Team redTeam, Team blackTeam) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
    }
}
