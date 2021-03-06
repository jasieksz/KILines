package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameState;


public class Serializer {
    private GameState gameState;
    private final Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();


    public Serializer(GameState gameState) {
        this.gameState = gameState;
    }

    public String serializeGameState() {
        return gson.toJson(gameState);
    }

    public String serializeMotorcycles() {
        return gson.toJson(gameState.getMotorcycles());
    }

    public String serializeBoard() {
        return gson.toJson(gameState.getBoard());
    }

    public String serializeMotorcyclesWithName() {
        return "{\"players\": " + serializeMotorcycles() + "}";
    }

    public String serializePowerUpsWithName() {
        return "{\"powerups\": " + serializePowerUps() + "}";
    }

    private String serializePowerUps() {
        return gson.toJson(gameState.getPowerups());
    }
}
