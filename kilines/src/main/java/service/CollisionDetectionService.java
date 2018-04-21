package service;

import model.GameState;
import model.Motorcycle;

public class CollisionDetectionService {
    public boolean detect(GameState gameState, Motorcycle motorcycle) {
        return gameState.getBoard().keySet().contains(motorcycle.getPosition());
    }
}
