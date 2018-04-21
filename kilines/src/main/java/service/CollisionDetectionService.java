package service;

import model.Award;
import model.GameState;
import model.Motorcycle;

public class CollisionDetectionService {
    public boolean detect(GameState gameState, Motorcycle motorcycle) {
        if (gameState.getBoard().containsKey(motorcycle.getPosition())) {
            final int playerId = gameState.getBoard().get(motorcycle.getPosition()).getPlayerId();
            for (int i = 0; i < gameState.getMotorcycles().size(); i++) {
                if (gameState.getMotorcycles().get(i).getPlayerId().getPlayerId() == playerId);
                    gameState.getMotorcycles().get(i).getScore().addScore(Award.COLLISION.getAward());
            }
            return true;
        }
        else return false;


    }
}
