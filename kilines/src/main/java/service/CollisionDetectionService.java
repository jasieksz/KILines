package service;

import model.Award;
import model.GameState;
import model.Motorcycle;
import model.Point;

import java.util.Optional;

public class CollisionDetectionService {
    public boolean detect(GameState gameState, Motorcycle motorcycle) {
//        Point myPosition = motorcycle.getPosition();
//        if (gameState.getBoard().containsKey(myPosition)){
//            String opponentPlayerID = gameState.getBoard().get(myPosition);
//            if (opponentPlayerID.equals(motorcycle.getPlayerNick())){ // WJECHALEM W SWOJA SCIANE
//
//            }
//        }
//
        if (gameState.getBoard().containsKey(motorcycle.getPosition())) {
            final String playerId = gameState.getBoard().get(motorcycle.getPosition());
            for (int i = 0; i < gameState.getMotorcycles().size(); i++) {
                if (gameState.getMotorcycles().get(i).getPlayerNick().equals(playerId)) {
                    gameState.getMotorcycles().get(i).getScore().addScore(Award.COLLISION.getAward());
                }

            }
            return true;
        }
        else return false;


    }
}
