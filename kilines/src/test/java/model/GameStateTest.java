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

        String player = "player";
        String player2 = "player2";

        Motorcycle motorcycle = new Motorcycle(player, point);
        Motorcycle motorcycle1 = new Motorcycle(player2,point1);

        Map<Point,String> board = gameState.getBoard();
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        motorcycles.add(motorcycle);
        motorcycles.add(motorcycle1);

        GameState gameState1 = new GameState(board,motorcycles);

        gameState1.changePlayerDirection(player2,Direction.RIGHT);

        List<Motorcycle> motorcyclesUpdated = gameState1.getMotorcycles();

        for(Motorcycle moto : motorcyclesUpdated){
            if(moto.getPlayerNick().equals(player))
                assertEquals(motorcycle.getDirection(), moto.getDirection());
            else assertEquals(Direction.RIGHT, moto.getDirection());
        }
    }

    @Test
    public void checkCollisionsTest(){
        GameState gameState = mock(GameState.class);
        Point point = mock(Point.class);

        Motorcycle motorcycle = new Motorcycle("player", point);
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        motorcycles.add(motorcycle);

        Map<Point,String> board = gameState.getBoard();

        Motorcycle collidingMotorcycle = new Motorcycle("player1", point);
        motorcycles.add(collidingMotorcycle);
        board.put(point,"player1");

        GameState gameState1 = new GameState(board,motorcycles);

        gameState1.checkCollisions();

        motorcycles = gameState1.getMotorcycles();
        for(Motorcycle moto : motorcycles){
            if(moto.getPlayerNick().equals("player"))
                assertEquals(false, moto.isAlive());
        }
    }

    @Test
    public void movePlayers(){
        String player = "player";
        GameState gameState = mock(GameState.class);
        Point point = mock(Point.class);

        Motorcycle motorcycle = new Motorcycle(player, point);
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        motorcycles.add(motorcycle);

        Map<Point,String> board = gameState.getBoard();
        board.put(point,motorcycle.getPlayerNick());

        GameState gameState1 = new GameState(board,motorcycles);

        gameState1.movePlayers();
        UpdatePositionService service = new UpdatePositionService();

        motorcycles = gameState1.getMotorcycles();
        for(Motorcycle moto : motorcycles) {
           if (moto.getPlayerNick().equals(player)) {
               int x = moto.getPosition().getX();
               int y = moto.getPosition().getY();
               service.update(moto, 1);
               assertEquals(x,moto.getPosition().getX());
               assertEquals(y-1,moto.getPosition().getY());
            }
        }
    }
}
