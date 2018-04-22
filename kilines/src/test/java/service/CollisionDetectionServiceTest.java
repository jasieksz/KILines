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
        PlayerIdentifier playerIdentifier = mock(PlayerIdentifier.class);

        Map<Point, PlayerIdentifier> board = gameState.getBoard();
        board.put(point,playerIdentifier);

        PlayerIdentifier id = new PlayerIdentifier(Color.RED,10);

        Motorcycle moto = new Motorcycle(id, point);
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
        PlayerIdentifier playerIdentifier = mock(PlayerIdentifier.class);

        Map<Point, PlayerIdentifier> board = gameState.getBoard();
        board.put(point,playerIdentifier);

        Motorcycle moto = new Motorcycle(playerIdentifier,point);
        List<Motorcycle> motorcycles = gameState.getMotorcycles();
        GameState game = new GameState(board,motorcycles);

        CollisionDetectionService collisionDetectionService = new CollisionDetectionService();

        for (int i = 0; i < gameState.getMotorcycles().size(); i++){
            Score score = gameState.getMotorcycles().get(i).getScore();
            int scoreInt = score.getScore();
            int playerId = gameState.getMotorcycles().get(i).getPlayerId().getPlayerId();
            if (playerId == game.getBoard().get(moto).getPlayerId()){
                assertEquals(scoreInt + Award.COLLISION.getAward(),score);
            }
        }
    }
}
