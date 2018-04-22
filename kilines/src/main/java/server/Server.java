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
    private GameState gameState = null;
    private GameState.InitializerBuilder builder = new GameState.InitializerBuilder(1)
            .boardX(GameUtils.boardX)
            .boardY(GameUtils.boardY)
            .addWalls();

    private boolean isStarted = false;

    private Serializer serializer = new Serializer(getGameState());

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