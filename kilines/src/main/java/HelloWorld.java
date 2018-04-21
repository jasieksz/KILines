import com.google.gson.Gson;
import model.GameState;
import model.Motorcycle;
import model.Point;
import server.Server;
import server.UpdatesWebSocketHandler;

import java.util.LinkedList;
import java.util.List;

import static spark.Spark.*;

public class HelloWorld {
    static List<String> users = new LinkedList<>();

    public static boolean isOccupied(String nick){
        return users.contains(nick);
    }

    public static void main(String[] args) {
        Gson gson = new Gson();

        webSocket("/game/websocket", UpdatesWebSocketHandler.class);

        staticFiles.location("/public");
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });


        get("/hello", (req, res) -> {
            return "{\"message\":\"Hello\"}";
        });

        get("/login/:nick", (req, res) -> {
            String nick = req.params(":nick");
            if (!isOccupied(nick)) {
                users.add(nick);
                return "{\"type\": \"login\", \"isOk\": true, \"token\": \"" + "adjfla" + "\"}";
            } else{
                return "{\"type\": \"login\", \"isOk\": false, \"msg\": \"\"Nickname occupid\"}";
            }

        });

        get("/init", (req, res) -> {
            return "{\n" +
                    "       \"type\" : \"init\",\n" +
                    "       \"players\": [\n" +
                    "            {\n" +
                    "                \"nick\": \"Andrzej\",\n" +
                    "                \"pos\": {\"x\": 500, \"y\": 500},\n" +
                    "                \"dir\": 0\n" +
                    "            }\n" +
                    "        ],\n" +
                    "        \"colors\": [[244, 66, 66],[143, 244, 65]],\n" +
                    "        \"obstacles\" :[\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 0}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 1}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 2}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 3}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 4}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 5}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 6}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 7}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 8}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 9}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 10}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 11}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 12}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 13}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 14}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 15}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 16}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 0,\"y\": 17}, \"color\": 0},\n" +
                    "            {\"pos\": {\"x\": 300,\"y\": 300}, \"color\": 1},\n" +
                    "            {\"pos\": {\"x\": 301,\"y\": 300}, \"color\": 1},\n" +
                    "            {\"pos\": {\"x\": 302,\"y\": 300}, \"color\": 1},\n" +
                    "            {\"pos\": {\"x\": 303,\"y\": 300}, \"color\": 1},\n" +
                    "            {\"pos\": {\"x\": 304,\"y\": 300}, \"color\": 1},\n" +
                    "            {\"pos\": {\"x\": 305,\"y\": 300}, \"color\": 1},\n" +
                    "            {\"pos\": {\"x\": 306,\"y\": 300}, \"color\": 1},\n" +
                    "            {\"pos\": {\"x\": 307,\"y\": 300}, \"color\": 1}\n" +
                    "        ]\n" +
                    "    }";
            //return getInit();
        });

        init();

    }

    private static String getInit() {
        Server server = new Server();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type: \"init\", players: [");
        GameState gameState = null; // server.getGameState(); TODO FIX THIS move to server
        List<Motorcycle> motorcycles = gameState.getMotorcycles();

        for (Motorcycle motorcycle: motorcycles) {
            Integer id = motorcycle.getPlayerId().getPlayerId();
            String nick = users.get(id);
            stringBuilder.append("{nick: \"" + nick + "\", pos: {");
            Point point = motorcycle.getPosition();
            stringBuilder.append("x: \"" + point.getX() + "\", y: \"" + point.getY() + "\" }");
        }

        stringBuilder.append("]}");

        return stringBuilder.toString();
    }
}