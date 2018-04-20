package game;

public class Motorcycle {
    private final int color;
    private Direction direction;
    private Point position;
    private int speed;

    public Motorcycle(int color, Point position) {
        this.color = color;
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
}
