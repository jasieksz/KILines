package model;

import java.util.List;
import java.util.Map;

public class GameState {
    private Map<Point, Walls> board;
    private List<Motorcycle> motorcycles;

    public GameState(Map<Point, Walls> board, List<Motorcycle> motorcycles) {
        this.board = board;
        this.motorcycles = motorcycles;
    }

    public Map<Point, Walls> getBoard() {
        return board;
    }

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }
}
