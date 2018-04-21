package service;

import model.Direction;
import model.Motorcycle;
import model.Point;

public class UpdatePositionService {

    public void update(Motorcycle motorcycle, int delta) {
        int x = motorcycle.getPosition().getX();
        int y = motorcycle.getPosition().getY();

        if (motorcycle.getDirection().equals(Direction.UP)){
            y -= delta;
        } else if (motorcycle.getDirection().equals(Direction.DOWN)){
            y += delta;
        } else if (motorcycle.getDirection().equals(Direction.LEFT)){
            x -= delta;
        } else {
            x += delta;
        }

        Point newPosition = new Point(x, y);
        motorcycle.setPosition(newPosition);
    }
}
