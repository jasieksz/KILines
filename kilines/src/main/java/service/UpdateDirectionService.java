package service;

import model.Direction;
import model.Motorcycle;

public class UpdateDirectionService {
    public void updateDirection(Motorcycle motorcycle, Direction direction) {
        motorcycle.setDirection(direction);
    }
}
