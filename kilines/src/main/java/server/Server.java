package server;

import controller.RestApi;
import model.GameState;
import model.Point;
import rx.Observable;
import serialization.Serializer;

import java.util.concurrent.TimeUnit;

public class Server {
    private GameState gameState = null;
    private GameState.InitializerBuilder builder = new GameState.InitializerBuilder(1)
            .boardX(GameUtils.boardX)
            .boardY(GameUtils.boardY)
            .addPlayer(new Point(10,10), "Jasike")
            .addWalls();

    private Serializer serializer = new Serializer(getGameState());
    private RestApi api;

    public void run(){
        System.out.println(serializer.serializeMotorcycles());
        System.out.println("STARTED");
        api = new RestApi(getGameState());
        api.loginUsersRequest();

        Observable.interval(GameUtils.interval, TimeUnit.MICROSECONDS)
                .subscribe(tick -> {
                    gameState.atomicMoveAndCollision();
                    api.getHandler().broadcast(serializer.serializeMotorcycles());
                    System.out.println(serializer.serializeMotorcycles());
                });
    }

    public GameState getGameState() {
        if (gameState == null){
            this.gameState = builder.build();
        }
        return gameState;
    }
}