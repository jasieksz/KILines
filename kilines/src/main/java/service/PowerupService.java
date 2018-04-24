package service;

import model.powerups.Powerup;
import model.powerups.Thicker;

import java.util.List;

public class PowerupService {
    public void checkIfPowerupIsActive(List<Powerup> powerups) {
        for (Powerup powerup : powerups) {
            if (((Thicker) powerup).getStepsLeft() == 0)
                powerups.remove(powerup);
            else if (((Thicker) powerup).getStepsLeft() > 0)
                ((Thicker) powerup).decreaseStepsLeft();
        }
    }
}
