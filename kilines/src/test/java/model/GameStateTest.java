package model;

import org.junit.Test;
import service.UpdatePositionService;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GameStateTest {

    @Test
    public void changePlayerDirectionTest(){
        GameState gameState = mock(GameState.class);
        Point point = mock(Point.class);
        Point point1 = mock(Point.class);

        Motorcycle motorcycle = new Motorcycle(new PlayerIdentifier(Color.BLUE, 2), point);
        Motorcycle motorcycle1 = new Motorcycle(new PlayerIdentifier(Color.RED,3),point1);

        Map<Point,PlayerIdentifier> board = gameState.getBoard();
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        motorcycles.add(motorcycle);
        motorcycles.add(motorcycle1);

        GameState gameState1 = new GameState(board,motorcycles);

        gameState1.changePlayerDirection(3,Direction.RIGHT);

        List<Motorcycle> motorcyclesUpdated = gameState1.getMotorcycles();

        for(Motorcycle moto : motorcyclesUpdated){
            if(moto.getPlayerId().getPlayerId() == 2)
                assertEquals(motorcycle.getDirection(), moto.getDirection());
            else assertEquals(Direction.RIGHT, moto.getDirection());
        }
    }

    @Test
    public void checkCollisionsTest(){
        GameState gameState = mock(GameState.class);
        Point point = mock(Point.class);

        Motorcycle motorcycle = new Motorcycle(new PlayerIdentifier(Color.WHITE,2), point);
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        motorcycles.add(motorcycle);

        Map<Point,PlayerIdentifier> board = gameState.getBoard();

        Motorcycle collidingMotorcycle = new Motorcycle(new PlayerIdentifier(Color.BLUE,3), point);
        motorcycles.add(collidingMotorcycle);
        board.put(point,new PlayerIdentifier(Color.BLUE,3));

        GameState gameState1 = new GameState(board,motorcycles);

        gameState1.checkCollisions();

        motorcycles = gameState1.getMotorcycles();
        for(Motorcycle moto : motorcycles){
            if(moto.getPlayerId().getPlayerId() == 3)
                assertEquals(false, moto.isAlive());
        }
    }

    @Test
    public void movePlayers(){
        int PLAYER_ID = 2;

        GameState gameState = mock(GameState.class);
        Point point = mock(Point.class);

        Motorcycle motorcycle = new Motorcycle(new PlayerIdentifier(Color.WHITE,PLAYER_ID), point);
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        motorcycles.add(motorcycle);

        Map<Point,PlayerIdentifier> board = gameState.getBoard();
        board.put(point,motorcycle.getPlayerId());

        GameState gameState1 = new GameState(board,motorcycles);

        gameState1.movePlayers();
        UpdatePositionService service = new UpdatePositionService();

        motorcycles = gameState1.getMotorcycles();
        for(Motorcycle moto : motorcycles) {
           if (moto.getPlayerId().getPlayerId() == PLAYER_ID) {
               int x = moto.getPosition().getX();
               int y = moto.getPosition().getY();
               service.update(moto, 1);
               assertEquals(x,moto.getPosition().getX());
               assertEquals(y-1,moto.getPosition().getY());
            }
        }

    }
}
