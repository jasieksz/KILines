package server;

import controller.RestApi;
import model.Color;
import model.GameState;
import model.PlayerIdentifier;
import model.Point;
import rx.Observable;
import serialization.Serializer;

import java.util.concurrent.TimeUnit;

public class Server {
    private GameState gameState = null;
    private GameState.InitializerBuilder builder = new GameState.InitializerBuilder(4)
            .boardX(GameUtils.boardX)
            .boardY(GameUtils.boardY)
            .addWalls();



    public void run(){
        RestApi api = new RestApi(gameState);
        api.loginUsersRequest();

        Observable.interval(GameUtils.interval, TimeUnit.MICROSECONDS)
                .subscribe(tick -> {
                    gameState.atomicMoveAndCollision();
                });
    }

    public GameState getGameState() {
        return gameState != null ? gameState : builder.build();
    }
}