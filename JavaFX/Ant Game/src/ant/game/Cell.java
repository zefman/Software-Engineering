/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jozefmaxted
 */
public class Cell {
    private int foodContained;
    private List<Integer> redPheromone;
    private List<Integer> blackPheromone;
    Type type;

    public Cell(Type type) {
        this.type = type;
        redPheromone = new ArrayList<Integer>();
        blackPheromone = new ArrayList<Integer>();
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

    public boolean getRedPheronome(int pheromone) {
        if (redPheromone.contains(pheromone)) {
            return true;
        } else {
            return false;
        }
    }

    public void setRedPheromone(int pheromone) {
        if (!redPheromone.contains(pheromone)) {
            redPheromone.add(pheromone);
        }
    }

    public boolean getBlackPheromone(int pheromone) {
        if (blackPheromone.contains(pheromone)) {
            return true;
        } else {
            return false;
        }
    }

    public void setBlackPheromone(int pheromone) {
        if (!blackPheromone.contains(pheromone)) {
            blackPheromone.add(pheromone);
        }
    }
    
    public void removeRedPheromone(int pheromone) {
        if (redPheromone.contains(pheromone)) {
            redPheromone.remove(pheromone);
        }
    }
    
    public void removeBlackPheromone(int pheromone) {
        if (blackPheromone.contains(pheromone)) {
            blackPheromone.remove(pheromone);
        }
    }
    
    public boolean redPheromoneEmpty() {
        if (redPheromone.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean blackPheromoneEmpty() {
        if (blackPheromone.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void reduceFood() {
            foodContained--;
    }
}
