package server;

import model.Color;
import model.GameState;
import model.PlayerIdentifier;
import model.Point;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class Server {
    public static void main(String[] args) throws Exception {

         GameState gameState = new GameState.InitializerBuilder(1)
                .boardX(640)
                .boardY(480)
                .addWalls()
                .addAGHWalls()
                .addPlayer(new Point(100,100), new PlayerIdentifier(Color.RED, 1))
                .build();

        Observable.interval(GameUtils.interval, TimeUnit.MICROSECONDS)
                .toBlocking()
                .subscribe(tick -> {
                    gameState.movePlayers();
                });
    }
}
