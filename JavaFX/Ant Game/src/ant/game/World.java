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
import java.util.ArrayList;

/**
 *
 * @author jozefmaxted
 */
public class World {
    Cell[] worldGrid;
    Ant[] antGrid;
    Team redTeam, blackTeam;
    

    public World(Team redTeam, Team blackTeam) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
	worldGrid = new Cell[16900];
        antGrid = new Ant[16900];
        
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
	worldGrid = new Cell[16900];
        antGrid = new Ant[16900];
        
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
        
        populateAnts();
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

    public void takeAntTurns() {
        ArrayList<Integer> antsThatHaveMoved = new ArrayList<Integer>();
        
        // Starting with the red and black ants with the smallest id
        for (int i = 0; i < (91+91); i++) {
            //Look for the cuurent ant
            for (int j = 0; j < antGrid.length; j++) {
                if (antGrid[j] != null && antGrid[j].getId() == i && !antsThatHaveMoved.contains(antGrid[j].getId())) {
                    antsThatHaveMoved.add(antGrid[j].getId());
                    takeTurn(antGrid[j], j);
                }
            }
        }
    }
    
    private void takeTurn(Ant ant, int index) {
        String currentCommand = "";
        if (ant.getColour()) {
            currentCommand = redTeam.getBrain().get(ant.getBrainState());
        } else {
            currentCommand = blackTeam.getBrain().get(ant.getBrainState());
        }
        
        String[] commandTokens = currentCommand.toLowerCase().split(" ");
        
        int nextDirection = antGrid[index].getDirection();
        
        switch (commandTokens[0]) {
            case "move":
                
                //Check to see if the cell it is going to move into is free
                int currentY = index/130;
                int currentX = index % 130;
                int nextCell = adjacentCell(currentX, currentY, ant.getDirection());
                
                //System.out.println(ant.getColour() + "Ant " + ant.getId() + " moving in cell" + currentX + " " + currentY + " to " + nextCell);
                
                if (worldGrid[nextCell].getType() != Cell.Type.ROCKY && antGrid[nextCell] == null) {
                    antGrid[nextCell] = ant;
                    antGrid[index] = null;
                    antGrid[nextCell].setBrainState(Integer.parseInt(commandTokens[1]));
                } else {
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                }
                break;
            case "turn":
                //System.out.println("Ant " + ant.getId() + " turning in cell");
                switch (commandTokens[1]) {
                    case "right":
                        nextDirection = antGrid[index].getDirection()+1;
                        if (nextDirection > 5) {
                            nextDirection = 0;
                        }
                        antGrid[index].setDirection(nextDirection);
                        antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                        break;
                    case "left":
                        nextDirection = antGrid[index].getDirection()-1;
                        if (nextDirection < 0) {
                            nextDirection = 5;
                        }
                        antGrid[index].setDirection(nextDirection);
                        antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                        break;
                      
                }
                break;
            case "sense":
                //System.out.println(currentCommand);
                switch (commandTokens[4]) {
                    case "mark":
                        int cellToCheck;
                        switch (commandTokens[1]) {
                            case "here":
                                cellToCheck = index;
                                break;
                            case "ahead":
                                cellToCheck = adjacentCell(index % 130, index/130, ant.getDirection());
                                break;
                            case "leftahead":
                                nextDirection = antGrid[index].getDirection()-1;
                                if (nextDirection < 0) {
                                    nextDirection = 5;
                                }
                                cellToCheck = adjacentCell(index % 130, index/130, nextDirection);
                                break;
                            case "rightahead":
                                nextDirection = antGrid[index].getDirection()+1;
                                if (nextDirection > 5) {
                                    nextDirection = 0;
                                }
                                cellToCheck = adjacentCell(index % 130, index/130, nextDirection);
                                break;
                        }
                        if (checkMark(index, Integer.parseInt(commandTokens[5]), false)) {
                            antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                        } else {
                            antGrid[index].setBrainState(Integer.parseInt(commandTokens[3]));
                        }
                        break;
                    default:
                        switch (commandTokens[1]) {
                            case "here":
                                cellToCheck = index;
                                break;
                            case "ahead":
                                cellToCheck = adjacentCell(index % 130, index/130, ant.getDirection());
                                break;
                            case "leftahead":
                                nextDirection = antGrid[index].getDirection()-1;
                                if (nextDirection < 0) {
                                    nextDirection = 5;
                                }
                                cellToCheck = adjacentCell(index % 130, index/130, nextDirection);
                                break;
                            case "rightahead":
                                nextDirection = antGrid[index].getDirection()+1;
                                if (nextDirection > 5) {
                                    nextDirection = 0;
                                }
                                cellToCheck = adjacentCell(index % 130, index/130, nextDirection);
                                break;
                        }
                        if (cellMatches(nextDirection, commandTokens[4], false)) {
                            antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                        } else {
                            //System.out.println(currentCommand);
                            antGrid[index].setBrainState(Integer.parseInt(commandTokens[3]));
                        }
                        break;
                }
                break;
            case "mark":
                if (ant.getColour()) {
                    //Red
                    worldGrid[index].setRedPheromone(Integer.parseInt(commandTokens[1]));
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                } else {
                    //Black
                    worldGrid[index].setBlackPheromone(Integer.parseInt(commandTokens[1]));
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                }
                break;
            case "unmark":
                if (ant.getColour()) {
                    //Red
                    worldGrid[index].setRedPheromone(-5);
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                } else {
                    //Black
                    worldGrid[index].setBlackPheromone(-5);
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                }
                break;
            case "pickup":
                if (worldGrid[index].getType() == Cell.Type.FOOD) {
                    antGrid[index].pickUpFood();
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[1]));
                    worldGrid[index].reduceFood();
                    if (worldGrid[index].foodLeft() <= 0) {
                        worldGrid[index].setType(Cell.Type.CLEAR);
                    }
                } else {
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[2]));
                }
                break;
            case "drop":
                antGrid[index].dropFood();
                antGrid[index].setBrainState(Integer.parseInt(commandTokens[1]));
                if (worldGrid[index].getType() == Cell.Type.REDANTHILL) {
                    redTeam.increaseCollectedFood();
                } else if (worldGrid[index].getType() == Cell.Type.BLACKANTHILL) {
                    blackTeam.increaseCollectedFood();
                } else {
                    worldGrid[index].setType(Cell.Type.FOOD);
                    worldGrid[index].giveFood();
                }
                break;
            case "flip":
                int randomNum = 0 + (int)(Math.random() * ((Integer.parseInt(commandTokens[1]) - 0) + 1));
                if (randomNum == 0) {
                   antGrid[index].setBrainState(Integer.parseInt(commandTokens[2])); 
                } else {
                    antGrid[index].setBrainState(Integer.parseInt(commandTokens[3]));
                }
                break;
            default:
                System.out.println(commandTokens[0]);
                ant.setBrainState(ant.getBrainState()+1);
                break;
        }
        
    }
    
    private boolean checkMark(int theCell, int theMark, boolean isRed) {
        if (isRed) {
            //Red
            if (worldGrid[theCell].getRedPheronome() == theMark) {
                return true;
            } else {
                return false;
            }
        } else {
            //Black
            if (worldGrid[theCell].getBlackPheronome() == theMark) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    private boolean cellMatches(int theCell, String condition, boolean isRed) {
        if (worldGrid[theCell].getType() == Cell.Type.ROCKY) {
            if (condition == "rock") {
                return true;
            } else {
                return false;
            }
        } else {
            switch (condition) {
                case "friend":
                    if (antGrid[theCell] != null && antGrid[theCell].getColour() == isRed) {
                        return true;
                    } else {
                        return false;
                    }
                case "foe":
                    if (antGrid[theCell] != null && antGrid[theCell].getColour() != isRed) {
                        return true;
                    } else {
                        return false;
                    }
                case "friendwithfood":
                    if (antGrid[theCell] != null && antGrid[theCell].getColour() == isRed && antGrid[theCell].hasFood()) {
                        return true;
                    } else {
                        return false;
                    }
                case "foewithfood":
                    if (antGrid[theCell] != null && antGrid[theCell].getColour() != isRed && antGrid[theCell].hasFood()) {
                        return true;
                    } else {
                        return false;
                    }
                case "food":
                    if (worldGrid[theCell].getType() == Cell.Type.FOOD) {
                        return true;
                    } else {
                        return false;
                    }
                case "foemarker":
                    //First check colour
                    if (isRed) {
                        //Red
                        if (worldGrid[theCell].getBlackPheronome() != -5) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        //Black
                        if (worldGrid[theCell].getRedPheronome() != -5) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                case "home":
                    if (isRed && worldGrid[theCell].getType() == Cell.Type.REDANTHILL) {
                        return true;
                    } else if (!isRed && worldGrid[theCell].getType() == Cell.Type.BLACKANTHILL) {
                        return true;
                    } else {
                        return false;
                    }
                case "foehome":
                    if (isRed && worldGrid[theCell].getType() == Cell.Type.BLACKANTHILL) {
                        return true;
                    } else if (!isRed && worldGrid[theCell].getType() == Cell.Type.REDANTHILL) {
                        return true;
                    } else {
                        return false;
                    }
            }
        }
        return false;
    }
    
    private int adjacentCell(int x, int y, int d) {
        int newX = 0;
        int newY = 0;
        
        switch (d) {
            case 0:
                newX = x + 1;
                newY = y;
                break;
            case 1:
                if (y % 2 == 0) {
                    newX = x;
                    newY = y +1;
                } else {
                    newX = x + 1;
                    newY = y + 1;
                }
                break;
            case 2:
                if (y % 2 == 0) {
                    newX = x - 1;
                    newY = y +1;
                } else {
                    newX = x;
                    newY = y + 1;
                }
                break;
            case 3:
                newX = x - 1;
                newY = y;
                break;
            case 4:
                if (y % 2 == 0) {
                    newX = x - 1;
                    newY = y - 1;
                } else {
                    newX = x;
                    newY = y - 1;
                }
                break;
            case 5:
                if (y % 2 == 0) {
                    newX = x;
                    newY = y - 1;
                } else {
                    newX = x + 1;
                    newY = y - 1;
                }
                break;
        }
        return newY * 130 + newX;
    }

    private void checkDeadAnts() {

    }

    public Ant[] getAntGrid() {
		return antGrid;
    }
    
    public void setTeams(Team redTeam, Team blackTeam) {
        this.redTeam = redTeam;
        this.blackTeam = blackTeam;
    }
    
    public void populateAnts() {
        int redAntCounter = 0;
        int blackAntCounter = 1;
        
        //First add red ants
        for (int i = 0; i < worldGrid.length; i++) {
            
            switch (worldGrid[i].getType()) {
                case REDANTHILL:
                    antGrid[i] = new Ant(redAntCounter, true);
                    redAntCounter = redAntCounter + 2;
                    
                    break;
                case BLACKANTHILL:
                    antGrid[i] = new Ant(blackAntCounter, false);
                    blackAntCounter = blackAntCounter + 2;
                    
                    break;
                default:
                    //Do nothing
                    break;
            }
            
        }
        
        System.out.println(redAntCounter);
        System.out.println(blackAntCounter);
    }
}
