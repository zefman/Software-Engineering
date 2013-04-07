/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

/**
 *
 * @author jozefmaxted
 */
public class Cell {
    private int foodContained;
    private int redPheromone;
    private int blackPheromone;
    Type type;

    public Cell(Type type) {
        this.type = type;
        redPheromone = -5;
        blackPheromone = -5;
    }

    public enum Type {
            CLEAR, ROCKY, REDANTHILL, BLACKANTHILL, REDANT, BLACKANT, FOOD, REDWITHFOOD, BLACKWITHFOOD
    }

    public void setType(Type type) {
            this.type = type;
    }

    public void giveFood() {
            foodContained=5;
    }
    
    public int foodLeft() {
        return foodContained;
    }

    public Type getType() {
            return type;
    }

    public int getRedPheronome() {
            return redPheromone;
    }

    public void setRedPheromone(int pheromone) {
            redPheromone = pheromone;
    }

    public int getBlackPheronome() {
            return blackPheromone;
    }

    public void setBlackPheromone(int pheromone) {
            blackPheromone = pheromone;
    }

    public void reduceFood() {
            foodContained--;
    }
}
