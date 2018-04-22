package model;

import service.CollisionDetectionService;
import service.UpdateDirectionService;
import service.UpdatePositionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class GameState {
    private Map<Point, PlayerIdentifier> board;
    private List<Motorcycle> motorcycles;

    public GameState(Map<Point, PlayerIdentifier> board, List<Motorcycle> motorcycles) {
        this.board = board;
        this.motorcycles = motorcycles;
    }

    private UpdatePositionService updatePositionService = new UpdatePositionService();
    private CollisionDetectionService collisionDetectionService = new CollisionDetectionService();
    private UpdateDirectionService updateDirectionService = new UpdateDirectionService();

    public Map<Point, PlayerIdentifier> getBoard() {
        return board;
    }

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void addPlayer(PlayerIdentifier id, Point pos){
        motorcycles.add(new Motorcycle(id, pos));
        return;
    }

    public void movePlayers() {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .forEach(motorcycle -> {
                    for (int i = 0; i < motorcycle.getSpeed(); i++) {
                        updatePositionService.update(motorcycle, 1);
                        if (!collisionDetectionService.detect(this, motorcycle)) {
                            board.put(motorcycle.getPosition(), motorcycle.getPlayerId());
                        } else {
                            break;
                        }
                    }
                });
    }

    public void checkCollisions() {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .filter(motorcycle -> collisionDetectionService.detect(this, motorcycle))
                .forEach(motorcycle -> motorcycle.setAlive(false));
    }

    public void changePlayerDirection(int playerId, Direction direction) {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .filter(motorcycle -> motorcycle.getPlayerId().getPlayerId() == playerId)
                .forEach(motorcycle -> updateDirectionService.updateDirection(motorcycle, direction));
    }

    /*
     * GAME STATE INITIALIZER
     */
    public static class InitializerBuilder {
        private final int minPlayers;
        private int x;
        private int y;

        private final PlayerIdentifier wall = new PlayerIdentifier(Color.WHITE, 0);
        private Map<Point, PlayerIdentifier> board = new HashMap<>();
        private List<Motorcycle> motorcycleList = new ArrayList<>();

        public InitializerBuilder(int mp) {
            minPlayers = mp;
        }

        public InitializerBuilder boardX(int x) {
            this.x = x;
            return this;
        }

        public InitializerBuilder boardY(int y) {
            this.y = y;
            return this;
        }

        public InitializerBuilder addPlayer(Point initialPosition, PlayerIdentifier id) {
            motorcycleList.add(new Motorcycle(id, initialPosition));
            return this;
        }

        public InitializerBuilder addWalls() {
            createWalls();
            return this;
        }

        public InitializerBuilder addAGHWalls(){
            createAGHThemeWalls();
            return this;
        }

        public GameState build() {
            while (motorcycleList.size() < minPlayers){
                addBot(new Point(0,0), wall); // TODO: Prepare for robot uprising
            }
            return new GameState(this.board, this.motorcycleList);
        }


        private void addBot(Point initialPosition, PlayerIdentifier id) {
            motorcycleList.add(new Motorcycle(id, initialPosition));
        }

        private void createWalls() {
            IntStream.range(0, x).forEach(i -> {
                board.put(new Point(i, 0), wall);
                board.put(new Point(i, y - 1), wall);
            });
            IntStream.range(0, y).forEach(i -> {
                board.put(new Point(0, i), wall);
                board.put(new Point(x - 1, i), wall);
            });
        }

        private void createAGHThemeWalls(){
            return;
        }


    }
}
