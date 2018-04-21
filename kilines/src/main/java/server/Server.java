package server;

import model.GameState;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class Server {
    private static GameState gameState;
    public static void main(String[] args) throws Exception {

         gameState = new Initializer(GameUtils.boardX, GameUtils.boardy).initializeGameState();

        Observable.interval(GameUtils.interval, TimeUnit.MICROSECONDS)
                .toBlocking()
                .subscribe(tick -> {
                    gameState.movePlayers();
                });
    }

    public GameState getGameState(){
        return gameState;
    }
}
