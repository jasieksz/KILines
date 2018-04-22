package model.powerups;

import model.Award;
import model.Point;

public abstract class Powerup {
    private Point position;
    private Award award;

    public Powerup(Point position, Award award) {
        this.position = position;
        this.award = award;
    }

    @Override
    abstract public String toString();

    public Point getPosition() {
        return position;
    }

    public Award getAward() {
        return award;
    }
}
