package service;

import model.GameState;
import model.Motorcycle;
import model.powerups.Powerup;
import model.powerups.Thicker;

public class CollisionDetectionWithPowerupService {
    public void detect(GameState gameState, Motorcycle motorcycle) {
        for (int i = 0; i < gameState.getPowerups().size(); i++) {
            Powerup currentPowerup = gameState.getPowerups().get(i);
            if (motorcycle.getPosition().equals(currentPowerup.getPosition())) {
                motorcycle.getScore().addScore(currentPowerup.getAward().getAward());

                switch (currentPowerup.toString()) {
                    case "Thicker":
                        motorcycle.addActivePowerup(currentPowerup);
                }
            }
        }
    }
}
