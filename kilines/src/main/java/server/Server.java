package server;

import controller.RestApi;
import model.Direction;
import model.GameState;
import model.Point;
import rx.Observable;
import serialization.Serializer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Server {
    private GameState gameState = null;
    private GameState.InitializerBuilder builder = new GameState.InitializerBuilder(1)
            .boardX(GameUtils.boardX)
            .boardY(GameUtils.boardY)
            .addWalls();

    private Serializer serializer = new Serializer(getGameState());

    public void run(RestApi api){

        Observable.interval(GameUtils.interval, TimeUnit.MILLISECONDS)
                .subscribe(tick -> {
                    gameState.movePlayers();
                    api.getHandler().broadcast(serializer.serializeMotorcycles());
                });
    }

    public GameState getGameState() {
        if (gameState == null){
            this.gameState = builder.build();
        }
        return gameState;
    }
}