package server;

import model.GameState;

import java.util.Map;

public class Server {

    private GameState gameState = new GameState(null, null);

    public static void main(String[] args) {
        System.out.println("");
    }

    public GameState getGameState() {
        return gameState;
    }
}
