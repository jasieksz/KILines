package model.powerups;

import model.Award;
import model.Point;

public class Thicker extends Powerup {
    public static final int MAX = 20;
    private int stepsLeft;

    public Thicker(Point position) {
        super(position, Award.NO_AWARD);
        this.stepsLeft = MAX;
    }

    public void decreaseStepsLeft() {
        this.stepsLeft--;
    }

    public int getStepsLeft() {
        return stepsLeft;
    }

    @Override
    public String toString() {
        return "Thicker";
    }
}
