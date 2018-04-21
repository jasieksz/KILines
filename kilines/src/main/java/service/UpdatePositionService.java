package service;

import model.Direction;
import model.Motorcycle;
import model.Point;

public class UpdatePositionService {

    public void update(Motorcycle motorcycle) {
        int x = motorcycle.getPosition().getX();
        int y = motorcycle.getPosition().getY();

        if (motorcycle.getDirection().equals(Direction.UP)){
            y -= motorcycle.getSpeed();
        } else if (motorcycle.getDirection().equals(Direction.DOWN)){
            y += motorcycle.getSpeed();
        } else if (motorcycle.getDirection().equals(Direction.LEFT)){
            x -= motorcycle.getSpeed();
        } else {
            x += motorcycle.getSpeed();
        }

        Point newPosition = new Point(x, y);
        motorcycle.setPosition(newPosition);
    }
}
