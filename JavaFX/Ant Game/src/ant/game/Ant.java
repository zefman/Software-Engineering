/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ant.game;

/**
 *
 * @author jozefmaxted
 */
public class Ant {
    private int id;
    private boolean isRed;
    private int direction;
    private int resting;
    private boolean hasFood;
    private int brainState;

    public Ant(int id, boolean isRed) {
            this.id = id;
            this.isRed = isRed;
            direction=0;
            resting = 0;
            hasFood = false;
            brainState = 0;
    }

    public void pickUpFood() {
            if (!hasFood) {
                    hasFood=true;
            }
    }

    public void dropFood() {
            if (hasFood) {
                    hasFood=false;
            }
    }

    public void setDirection(int direct) {
            direction = direct;
    }

    public int getId() {
            return id;
    }

    public void setResting(int restingTime) {
            resting = restingTime;
    }

    public boolean getColour() {
            return isRed;
    }

    public int getBrainState() {
            return brainState;
    }

    public void setBrainState(int brainState) {
            this.brainState = brainState;
    }
    
    /*
    public int[] sense(int senseDirection) {
        return new int[];
    }*/
}
