package service;

import model.*;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

public class UpdateDirectionServiceTest {

    @Test
    public void updateDirectionTest(){
        Point point = mock(Point.class);
        Motorcycle motorcycle = new Motorcycle("player", point);

        Direction direction = Direction.RIGHT;
        int dir = direction.getDir();

        UpdateDirectionService updateService = new UpdateDirectionService();
        updateService.updateDirection(motorcycle,direction);

        int updatedDirection = motorcycle.getDirection().getDir();
        assertSame(dir, updatedDirection);
    }
}
