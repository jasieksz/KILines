package server;

import controller.RestApi;
import model.Direction;
import model.GameState;
import model.Point;
import rx.Observable;
import rx.Subscription;
import serialization.Serializer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Server {

    private GameState gameState;
    private Serializer serializer;
    private boolean isStarted;

    public Server() {
        gameState = new GameState.InitializerBuilder(1)
            .boardX(GameUtils.boardX)
            .boardY(GameUtils.boardY)
            .addWalls()
            .addAGHWalls()
            .build();

        serializer = new Serializer(getGameState());
    }

    public void stop(){
        this.isStarted = false;
    }

    public void start(){
        this.isStarted = true;
    }

    public void run(RestApi api){

        Observable.interval(GameUtils.interval, TimeUnit.MILLISECONDS)
                .filter((e) -> this.isStarted)
                .subscribe(tick -> {
                    gameState.atomicMoveCollisionUpdate();
                    api.getHandler().broadcast(serializer.serializeMotorcyclesWithName());
                    api.getHandler().broadcast(serializer.serializePowerUpsWithName());
                });

        Observable.interval(3, TimeUnit.SECONDS)
                .subscribe(tick -> {
                    gameState.generatePowerup();
                });
    }

    public GameState getGameState() {
        return gameState;
    }
}