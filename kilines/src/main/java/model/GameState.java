package model;

import server.GameUtils;
import service.CollisionDetectionService;
import service.UpdateDirectionService;
import service.UpdatePositionService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class GameState {
    private Map<Point, String> board;
    private List<Motorcycle> motorcycles;


    public GameState(Map<Point, String> board, List<Motorcycle> motorcycles) {
        this.board = board;
        this.motorcycles = motorcycles;
    }

    private UpdatePositionService updatePositionService = new UpdatePositionService();
    private CollisionDetectionService collisionDetectionService = new CollisionDetectionService();
    private UpdateDirectionService updateDirectionService = new UpdateDirectionService();

    public Map<Point, String> getBoard() {
        return board;
    }

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void addPlayer(String nick, Point pos){
        motorcycles.add(new Motorcycle(nick, pos));
    }

    private void movePlayers() {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .forEach(motorcycle -> {
                    for (int i = 0; i < motorcycle.getSpeed(); i++) {
                        updatePositionService.update(motorcycle, 1);
                        if (!collisionDetectionService.detect(this, motorcycle)) {
                            board.put(motorcycle.getPosition(), motorcycle.getPlayerNick());
                        } else {
                            break;
                        }
                    }
                });
    }

    private void checkCollisions() {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .filter(motorcycle -> collisionDetectionService.detect(this, motorcycle))
                .forEach(motorcycle -> motorcycle.setAlive(false));
    }

    public void changePlayerDirection(String playerId, Direction direction) {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .filter(motorcycle -> motorcycle.getPlayerNick().equals(playerId))
                .forEach(motorcycle -> updateDirectionService.updateDirection(motorcycle, direction));
    }

    public void atomicMoveAndCollision() {
        movePlayers();
        checkCollisions();
    }

    public Optional<Motorcycle> getGameUserByNickname(String nick){
       return motorcycles.stream().filter(e-> e.getPlayerNick().equals(nick)).findFirst();
    }

    /*
     * GAME STATE INITIALIZER
     */
    public static class InitializerBuilder {
        private final int minPlayers;
        private int x;
        private int y;


        private Map<Point, String> board = new ConcurrentHashMap<>();
        private List<Motorcycle> motorcycleList = new CopyOnWriteArrayList<>();

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

        public InitializerBuilder addPlayer(Point initialPosition, String id) {
            motorcycleList.add(new Motorcycle(id, initialPosition));
            return this;
        }

        public InitializerBuilder addWalls() {
            createWalls();
            return this;
        }

        public InitializerBuilder addAGHWalls() {
            try {
                createAGHThemeWalls();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public GameState build() {
//            while (motorcycleList.size() < minPlayers){
//                addBot(new Point(0,0), GameUtils.WALL); // TODO: Prepare for robot uprising
//            }
            return new GameState(this.board, this.motorcycleList);
        }


        private void addBot(Point initialPosition, String id) {
            motorcycleList.add(new Motorcycle(id, initialPosition));
        }

        private void createWalls() {
            IntStream.range(0, x).forEach(i -> {
                board.put(new Point(i, 0), GameUtils.WALL);
                board.put(new Point(i, y - 1), GameUtils.WALL);
            });
            IntStream.range(0, y).forEach(i -> {
                board.put(new Point(0, i), GameUtils.WALL);
                board.put(new Point(x - 1, i), GameUtils.WALL);
            });
        }

        private void createAGHThemeWalls() throws IOException {
            BufferedReader br = new BufferedReader(new FileReader("/board_image/walls_to_add.txt"));
            try {
                String line = br.readLine();

                while (line != null) {
                    System.out.println(line);
                    String[] point = line.split("\\s+");

                    board.put(new Point(Integer.parseInt(point[0]), Integer.parseInt(point[1])), GameUtils.WALL);

                    line = br.readLine();
                }
            } finally {
                br.close();
            }
            return;
        }


    }
}
