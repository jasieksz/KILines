package model;

public class Motorcycle {
    private final PlayerIdentifier playerId;
    private Direction direction;
    private Point position;
    private int speed;
    private boolean isAlive;
    private Score score;

    public Motorcycle(PlayerIdentifier playerId, Point position) {
        this.playerId = playerId;
        this.position = position;
        this.direction = Direction.UP;
        this.speed = 1;
        this.isAlive = true;
        this.score = score;
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

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
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

    public boolean isAlive() {
        return isAlive();
    }

    public Score getScore() {
        return score;
    }
}
