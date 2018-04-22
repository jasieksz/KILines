package service;

import model.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CollisionDetectionServiceTest{

    @Test
    public void detectCollisionTest(){
        GameState gameState = mock(GameState.class);
        Point point = mock(Point.class);

        String playerNick = "player";
        Map<Point, String> board = gameState.getBoard();
        board.put(point,playerNick);

        PlayerIdentifier id = new PlayerIdentifier(Color.RED,10);

        Motorcycle moto = new Motorcycle(playerNick, point);
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        motorcycles.add(moto);
        GameState game = new GameState(board,motorcycles);

        CollisionDetectionService collisionDetectionService = new CollisionDetectionService();
        assertEquals(true,collisionDetectionService.detect(game,moto));
    }

    @Test
    public void addsScoreTest(){
        GameState gameState = mock(GameState.class);
        Point point = mock(Point.class);
        String playerNick = "player";

        Map<Point, String> board = gameState.getBoard();
        board.put(point,playerNick);

        Motorcycle moto = new Motorcycle(playerNick,point);
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        GameState game = new GameState(board,motorcycles);

        CollisionDetectionService collisionDetectionService = new CollisionDetectionService();

        for (int i = 0; i < gameState.getMotorcycles().size(); i++){
            Score score = gameState.getMotorcycles().get(i).getScore();
            int scoreInt;
            scoreInt = score.getScore();
            String nick = gameState.getMotorcycles().get(i).getPlayerNick();
            if (nick == game.getBoard().get(moto)){
                assertEquals(scoreInt + Award.COLLISION.getAward(),score);
            }
        }
    }
}
