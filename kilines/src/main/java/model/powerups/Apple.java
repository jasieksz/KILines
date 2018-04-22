package model.powerups;

import model.Award;
import model.Point;

public class Apple extends Powerup {
    public Apple(Point position) {
        super(position,  Award.APPLE_AWARD);
    }

    @Override
    public String toString() {
        return "Apple";
    }
}
