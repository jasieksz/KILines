package server;

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
                .boardX(640)
                .boardY(480)
                .addWalls()
                .addAGHWalls()
                .addPlayer(new Point(100, 100), new PlayerIdentifier(Color.RED, 1))
                .addPlayer(new Point(400, 400), new PlayerIdentifier(Color.BLUE, 2))
                .build();

        Serializer ser = new Serializer(gameState);
        System.out.println(ser.serializeBoard());



//        Observable.interval(GameUtils.interval, TimeUnit.MICROSECONDS)
//                .toBlocking()
//                .subscribe(tick -> {
//                    gameState.movePlayers();
//                });

//        while(true){
//            gameState.movePlayers();
//            gameState.checkCollisions();
//            broadcast(Serialzier(gameState));
//        }
    }
}
