package model;

public class Motorcycle {
    private final PlayerIdentifier playerId;
    private Direction direction;
    private Point position;
    private int speed;

    public Motorcycle(PlayerIdentifier playerId, Point position) {
        this.playerId = playerId;
        this.position = position;
        this.direction = Direction.UP;
        this.speed = 1;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public PlayerIdentifier getPlayerId() {
        return playerId;
    }
}
