package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.GameState;
import model.PlayerIdentifier;
import model.Point;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Serializer {
    private final GameState gameState;
    private final Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();


    public Serializer(GameState gameState) {
        this.gameState = gameState;
    }

    public String serializeMotorcycles(){
        return gson.toJson(gameState.getMotorcycles());
    }

    public String serializeBoard() {
        //Type type = new TypeToken<Map<Point, PlayerIdentifier>>(){}.getType();
        return gson.toJson(gameState.getBoard()); //, type);
    }
}