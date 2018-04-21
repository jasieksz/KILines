package model;

import service.CollisionDetectionService;
import service.UpdateDirectionService;
import service.UpdatePositionService;

import java.util.List;
import java.util.Map;

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

    public void movePlayers(){
        motorcycles.forEach(motorcycle -> updatePositionService.update(motorcycle));
    }
    public void checkCollisions(){
        motorcycles.stream()
                .filter(Motorcycle::isAlive)
                .filter(motorcycle -> collisionDetectionService.detect(this, motorcycle))
                .forEach(motorcycle -> motorcycle.setAlive(false));
    }
    public void changePlayerDirection(int id){
        motorcycles.stream().filter(motorcycle -> true).forEach(motorcycle -> updateDirectionService.update(motorcycle));
    }
}
