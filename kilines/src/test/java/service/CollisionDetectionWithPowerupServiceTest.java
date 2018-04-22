package service;

import model.GameState;
import model.Motorcycle;
import model.powerups.Powerup;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class CollisionDetectionWithPowerupServiceTest {

    @Test
    public void detectTest(){
        GameState gameState = mock(GameState.class);
        Motorcycle motorcycle = mock(Motorcycle.class);

        gameState.generatePowerup();

        for(Powerup powerup : gameState.getPowerups()){
            CollisionDetectionWithPowerupService service = new CollisionDetectionWithPowerupService();
            service.detect(gameState,motorcycle);
            assertTrue(motorcycle.getActivePowerups().size() == 1);
        }
    }
}
