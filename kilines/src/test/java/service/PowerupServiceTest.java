package service;

import model.Point;
import model.powerups.Powerup;
import model.powerups.Thicker;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PowerupServiceTest {

    @Test
    public void checkIfPowerupIsActiveTest(){
        List<Powerup> powerUps = new LinkedList<>();

        Powerup apple = new Thicker(new Point(5,5));
        Powerup thicker = new Thicker(new Point(1,1));
        powerUps.add(apple);
        powerUps.add(thicker);

        int steps = ((Thicker) thicker).getStepsLeft();

        PowerupService powerupService = new PowerupService();
        powerupService.checkIfPowerupIsActive(powerUps);

        int updatedSteps = steps;
        for(Powerup powerup : powerUps){
            if(powerup.getClass() == Thicker.class){
                updatedSteps = ((Thicker) powerup).getStepsLeft();
            }
        }
        assertEquals(steps-1,updatedSteps);
    }
}
