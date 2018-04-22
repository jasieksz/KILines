package service;

import model.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UpdatePositionServiceTest {

    @Test
    public void updateTest(){
        Motorcycle motorcycle = new Motorcycle(new PlayerIdentifier(Color.BLUE,5),new Point(10,10));
        int delta = 5;

        int x = motorcycle.getPosition().getX();
        int y = motorcycle.getPosition().getY();

        Direction direction = Direction.RIGHT;
        motorcycle.setDirection(direction);

        UpdatePositionService positionService = new UpdatePositionService();
        positionService.update(motorcycle,delta);

        Point point = new Point(x+5,y);

        assertEquals(point.getX(),motorcycle.getPosition().getX());
        assertEquals(point.getY(),motorcycle.getPosition().getY());

    }
}
