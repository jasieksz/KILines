import com.google.gson.Gson;
import model.GameState;
import model.Motorcycle;
import server.*;
import model.PlayerIdentifier;
import model.Point;

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
}