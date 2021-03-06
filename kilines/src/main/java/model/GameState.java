package model;

import model.powerups.Apple;
import model.powerups.Powerup;
import model.powerups.Thicker;
import server.GameUtils;
import service.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class GameState {
    private Map<Point, String> board;
    private List<Motorcycle> motorcycles;
    private List<Powerup> powerups = new ArrayList<>();
    private Map<Point, String> initialBoard;


    public GameState(Map<Point, String> board, List<Motorcycle> motorcycles) {
        this.board = board;
        this.initialBoard = new HashMap<>(board);
        this.motorcycles = motorcycles;
    }

    public void clearWalls() {
        System.out.println(this.board.size());
        this.board = new HashMap<>(this.initialBoard);
        System.out.println("clearing walls: ");
        System.out.println(this.board.size());
    }

    public void resurrectPlayers() {
        this.motorcycles.forEach(e -> e.setAlive(true));
        System.out.println("resurrecting players: ");
    }

    private UpdatePositionService updatePositionService = new UpdatePositionService();
    private CollisionDetectionService collisionDetectionService = new CollisionDetectionService();
    private CollisionDetectionWithPowerupService collisionDetectionWithPowerService = new CollisionDetectionWithPowerupService();
    private UpdateDirectionService updateDirectionService = new UpdateDirectionService();
    private PowerupService powerupService = new PowerupService();


    public Map<Point, String> getBoard() {
        return board;
    }

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void addPlayer(String nick, Point pos) {
        motorcycles.add(new Motorcycle(nick, pos));
    }

    public List<Powerup> getPowerups() {
        return powerups;
    }

    public void movePlayers() {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .forEach(motorcycle -> updatePositionService.update(motorcycle, 1));
    }


    private void updateBoard() {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .forEach(motorcycle -> board.put(motorcycle.getPosition(), motorcycle.getPlayerNick()));
    }

    private void checkCollisions() {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .filter(motorcycle -> collisionDetectionService.detect(this, motorcycle))
                .forEach(motorcycle -> motorcycle.setAlive(false));

        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .forEach(motorcycle -> {
                    collisionDetectionWithPowerService.detect(this, motorcycle);
                    powerupService.checkIfPowerupIsActive(motorcycle.getActivePowerups());
                });
    }

    public void atomicMoveCollisionUpdate() {
        movePlayers();
        checkCollisions();
        updateBoard();
    }

    public void changePlayerDirection(String playerId, Direction direction) {
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .filter(motorcycle -> motorcycle.getPlayerNick().equals(playerId))
                .forEach(motorcycle -> updateDirectionService.updateDirection(motorcycle, direction));
    }

    public void generatePowerup() {
        Random generator = new Random();
        Point point = new Point(generator.nextInt(GameUtils.boardX), generator.nextInt(GameUtils.boardY));

        if (board.containsKey(point))
            return;

        for (int i = 0; i < powerups.size(); i++)
            if (powerups.get(i).getPosition().equals(point))
                continue;

        int idPowerup = generator.nextInt(1);
        Powerup powerup = null;

        switch (idPowerup) {
            case 0:
                powerup = new Apple(point);
                break;
            case 1:
                powerup = new Thicker(point);
        }
        powerups.add(powerup);
    }


    private void leaveFootprints(Motorcycle motorcycle) {
        board.put(motorcycle.getPosition(), motorcycle.getPlayerNick());

        if (motorcycle.getActivePowerups().contains(Thicker.class)) {
            Direction direction = motorcycle.getDirection();
            switch (direction) {
                case UP:
                case DOWN:
                    board.put(new Point(motorcycle.getPosition().getX() - 1, motorcycle.getPosition().getY()), motorcycle.getPlayerNick());
                    board.put(new Point(motorcycle.getPosition().getX() + 1, motorcycle.getPosition().getY()), motorcycle.getPlayerNick());
                    break;
                case LEFT:
                case RIGHT:
                    board.put(new Point(motorcycle.getPosition().getX(), motorcycle.getPosition().getY() - 1), motorcycle.getPlayerNick());
                    board.put(new Point(motorcycle.getPosition().getX(), motorcycle.getPosition().getY() + 1), motorcycle.getPlayerNick());

            }
        }

    }

    public Optional<Motorcycle> getGameUserByNickname(String nick) {
        return motorcycles.stream().filter(e -> e.getPlayerNick().equals(nick)).findFirst();
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
        private List<Powerup> powerupList = new ArrayList<>();

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
            return new GameState(this.board, this.motorcycleList);
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
            String root = System.getProperty("user.dir");
            String path = "/kilines/src/main/resources/board_image/walls_to_add.txt";

            StringBuilder sbuf = new StringBuilder();
            Formatter formatter = new Formatter(sbuf);
            formatter.format("%s%s", root, path);

            BufferedReader br = new BufferedReader(new FileReader(sbuf.toString()));
            try {
                String line = br.readLine();

                while (line != null) {
                    String[] point = line.split("\\s+");
                    board.put(new Point(Integer.parseInt(point[1]), Integer.parseInt(point[0])), GameUtils.WALL);
                    line = br.readLine();
                }
            } finally {
                br.close();
            }
        }


    }
}
