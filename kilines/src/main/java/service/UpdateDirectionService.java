package service;

import model.Direction;
import model.Motorcycle;

public class UpdateDirectionService {
    public void updateDirection(Motorcycle motorcycle, Direction direction) {
        if(direction == Direction.LEFT && motorcycle.getDirection() == Direction.RIGHT)
            return;

        if(direction == Direction.RIGHT && motorcycle.getDirection() == Direction.LEFT)
            return;

        if(direction == Direction.UP && motorcycle.getDirection() == Direction.DOWN)
            return;

        if(direction == Direction.DOWN && motorcycle.getDirection() == Direction.UP)
            return;

        motorcycle.setDirection(direction);
    }
}
