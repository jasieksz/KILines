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
    public static void main(String[] args) throws Exception {

        GameState gameState = new GameState.InitializerBuilder(2)
                .boardX(GameUtils.boardX)
                .boardY(GameUtils.boardY)
                .addWalls()
                .addAGHWalls()
                .addPlayer(new Point(100, 100), "Jasiek")
                .addPlayer(new Point(400, 400), "Stasiek")
                .build();

        Serializer ser = new Serializer(gameState);
        System.out.println(ser.serializeGameState());

        RestApi api = new RestApi(gameState);
        api.loginUsersRequest();


//        Observable.interval(GameUtils.interval, TimeUnit.MICROSECONDS)
//                .toBlocking()
//                .subscribe(tick -> {
//                    gameState.movePlayers();
//                });

//        while(true){
//            gameState.movePlayers();
//            gameState.checkCollisions();
//            api.getWebsocketHandler.broadcast(Serialzier(gameState));
//        }
    }
}