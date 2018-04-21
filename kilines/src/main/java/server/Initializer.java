package server;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Initializer {
    private final int x;
    private final int y; //magic numbers -- bottom-right corner of board
    private final PlayerIdentifier wall = new PlayerIdentifier(Color.WHITE, 0);
    private Map<Point, PlayerIdentifier> board = new HashMap<>();
    private List<Motorcycle> motorcycleList = new ArrayList<>();

    public Initializer(int x, int y) {
        this.x = x;
        this.y = y;
        createWalls();
    }

    private void createWalls() {
        IntStream.range(0, x).forEach(i -> {
            board.put(new Point(i, 0), wall);
            board.put(new Point(i, y-1), wall);
        });
        IntStream.range(0, y).forEach(i -> {
            board.put(new Point(0, i), wall);
            board.put(new Point(x-1, i), wall);
        });
    }

    private void createMotorcycle(Point initialPosition, PlayerIdentifier id) throws Exception {
        if (motorcycleList.size() < 4){
            motorcycleList.add(new Motorcycle(id, initialPosition));
        } else {
            throw new Exception("Too many players");
        }
    }

    public GameState initializeGameState() throws Exception {
        int dx = x/4;
        int dy = y/4;
        createMotorcycle(new Point(dx, dy), new PlayerIdentifier(Color.RED, 1));
        createMotorcycle(new Point(x-dx, y-dy), new PlayerIdentifier(Color.BLUE, 2));
        return new GameState(board, motorcycleList);
    }


}
