package server;

import model.GameState;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws Exception {

        GameState gameState = new Initializer(GameUtils.boardX, GameUtils.boardy).initializeGameState();

        Observable.interval(GameUtils.interval, TimeUnit.MICROSECONDS)
                .toBlocking()
                .subscribe(tick -> {
                    gameState.movePlayers();
                });
    }
}
