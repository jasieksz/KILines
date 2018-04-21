package server;

import model.Color;
import model.GameState;
import model.PlayerIdentifier;
import model.Point;
import rx.Observable;

import com.google.gson.Gson;
import model.GameState;
import model.Motorcycle;

import org.json.simple.*;
import server.*;
import model.PlayerIdentifier;
import model.Point;

import javax.json.Json;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

import java.util.Map;

public class Server {

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
