package model;

public class Motorcycle {
    private final String nick;
    private Direction dir;
    private Point pos;
    private int speed;
    private boolean isAlive;
    private Score score;

    public Motorcycle(String playerId, Point position) {
        this.nick = playerId;
        this.pos = position;
        this.dir = Direction.UP;
        this.speed = 1;
        this.isAlive = true;
        this.score = new Score();
    }

    public Point getPosition() {
        return pos;
    }

    public void setPosition(Point position) {
        this.pos = position;
    }

    public Direction getDirection() {
        return dir;
    }

    public void setDirection(Direction direction) {
        this.dir = direction;
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

    public String getPlayerId() {
        return nick;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public Score getScore() {
        return score;
    }

}
