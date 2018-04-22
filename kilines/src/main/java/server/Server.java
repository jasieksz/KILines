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
            .addPlayer(new Point(10, 10), "Jasiek")
            .addWalls();

    private Serializer serializer = new Serializer(getGameState());
    private RestApi api;

    public void run(){
        api = new RestApi(getGameState());
        api.loginUsersRequest();

        Observable.interval(GameUtils.interval, TimeUnit.MILLISECONDS)
                .subscribe(tick -> {
                    gameState.atomicMoveCollisionUpdate();
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