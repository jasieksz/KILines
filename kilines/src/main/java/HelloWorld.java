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

public class HelloWorld {
    static Map<Integer, String> users = new HashMap<>();

    public static void main(String[] args) {
        Gson gson = new Gson();

        staticFiles.location("/public");
        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        get("/hello", (req, res) -> {
            return "{\"message\":\"Hello\"}";
        });

        get("/login/:nick", (req, res)-> {
            String nick = req.params(":nick");
            boolean isOccupided = false; // TODO + token
            if (!users.containsValue(nick)) {
                users.put(users.size(), nick);
                return "{\"type\": \"login\", \"isOk\": true, \"token\": \"token_id\"}";
            } else{
                return "{\"type\": \"login\", \"isOk\": false, \"msg\": \"\"Nickname occupid\"}";
            }

        });

        get("/init", (req, res) -> {
            return "{type: \"init\", players: [{ nick: \"bob\", pos: {x: 10, y: 10}}]}";
            //return getInit();
        });

        get("/update", (req, res) -> {
            String message;
            JSONObject json = new JSONObject();
            json.put("type", "update");

            JSONObject jsonPos = new JSONObject();
            jsonPos.put("x", 10);
            jsonPos.put("y", 11);

            JSONArray array = new JSONArray();
            JSONObject item = new JSONObject();
            item.put("nick", "bob");
            item.put("pos", jsonPos);
            item.put("dir", "right");
            item.put("isAlive", true);
            array.add(item);

            json.put("players", array);
            message = json.toString();
            return message;

            //return getUpdates();
        });

        put("/update/:nick/:dir", (req, res) -> {
            String nick = req.params(":nick");
            String dir = req.params(":dir");
            return "rer";

        });
    }

    private static String getInit() {
        Server server = new Server();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type: \"init\", players: [");
        GameState gameState = server.getGameState();
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

    private static String getUpdates() {
        return "re";
    }
}