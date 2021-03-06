package controller;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import server.GameUtils;
import server.Server;
import server.UpdatesWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static spark.Spark.*;


public class RestApi {

    private GameState gameState;
    private UpdatesWebSocketHandler handler;

    public RestApi(GameState gameState) {
        this.gameState = gameState;
        handler = new UpdatesWebSocketHandler(gameState);
    }

    public UpdatesWebSocketHandler getHandler() {
        return handler;
    }

    public void loginUsersRequest(Server server) {

        webSocket("/game/websocket", handler);

        staticFiles.location("/public");

        get("/login/:nick", (req, res) -> {
            String nick = req.params(":nick");
            return generateLoginRequest(nick).toJSONString();
        });


        get("/init", (req, res) -> generateInitRequest(this.gameState).toJSONString());

        RestApi thisApi = this;

        get("/start", (req, res) -> {
            System.out.println("Starting...");
            server.start();
            //server.run(thisApi);
            return "204";
        });

        get("/reset", (req, res) -> {
            System.out.println("Reseting...");
            server.stop();
            server.getGameState().clearWalls();
            server.getGameState().resurrectPlayers();
            return "204";
        });

        get("/do_stop", (req, res) -> {
            server.stop();
            return "stopped";
        });

        get("/do_start", (req, res) -> {
            server.start();
            return "started";
        });

        init();
    }

    private JSONObject generateInitRequest(GameState builder) {
        JSONObject json = new JSONObject();
        json.put("type", "init");

        JSONArray arrayMotorcycle = new JSONArray();

        for (Motorcycle motorcycle : builder.getMotorcycles()) {
            JSONObject item = new JSONObject();

            item.put("nick", motorcycle.getPlayerNick());

            JSONObject jsonPos = new JSONObject();
            jsonPos.put("x", motorcycle.getPosition().getX());
            jsonPos.put("y", motorcycle.getPosition().getY());

            item.put("pos", jsonPos);
            arrayMotorcycle.add(item);
        }
        json.put("players", arrayMotorcycle);


        JSONArray arrayObstacles = new JSONArray();
        for (Map.Entry<Point, String> entry : builder.getBoard().entrySet()) {
            JSONObject item = new JSONObject();

            JSONObject jsonPos = new JSONObject();
            jsonPos.put("x", entry.getKey().getX());
            jsonPos.put("y", entry.getKey().getY());
            item.put("pos", jsonPos);

            arrayObstacles.add(item);
        }
        json.put("obstacles", arrayObstacles);

        return json;
    }

    private JSONObject generateLoginRequest(String nick) {
        JSONObject json = new JSONObject();

        json.put("type", "login");
        boolean isOk = !gameState.getGameUserByNickname(nick).isPresent();
        json.put("isOk", isOk);

        if (isOk) {
            json.put("token", 33);
            int x = ThreadLocalRandom.current().nextInt(20, GameUtils.boardX - 20);
            int y = ThreadLocalRandom.current().nextInt(20, GameUtils.boardY - 20);
            while (gameState.getBoard().containsKey(new Point(x, y))) {
                x = ThreadLocalRandom.current().nextInt(20, GameUtils.boardX - 20);
                y = ThreadLocalRandom.current().nextInt(20, GameUtils.boardY - 20);
            }

            gameState.addPlayer(nick, new Point(x, y));

        } else {
            json.put("msg", "Nickname occupied");
        }
        return json;
    }

}
