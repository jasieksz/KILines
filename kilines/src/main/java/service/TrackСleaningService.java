package service;

import model.GameState;
import model.Motorcycle;
import model.Point;

public class TrackСleaningService {
    public void trigger(GameState gameState, Motorcycle motorcycle) {
        for (Point point : gameState.getBoard().keySet())
            if (gameState.getBoard().get(point).equals(motorcycle.getPlayerId()))
                gameState.getBoard().remove(point);
    }
}
