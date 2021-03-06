package model;

import org.eclipse.jetty.websocket.api.Session;

import model.powerups.Powerup;

import java.util.ArrayList;
import java.util.List;

public class Motorcycle {
    private final String nick;
    private Direction dir;
    private Point pos;
    private int speed;
    private boolean isAlive;
    private Score score;
    private List<Powerup> activePowerups;

    public Motorcycle(String playerId, Point position) {
        this.nick = playerId;
        this.pos = position;
        this.dir = Direction.UP;
        this.speed = 1;
        this.isAlive = true;
        this.score = new Score();
        this.activePowerups = new ArrayList<>();
    }

    public List<Powerup> getActivePowerups() {
        return activePowerups;
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

    public String getPlayerNick() {
        return nick;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public Score getScore() {
        return score;
    }

    public void addActivePowerup(Powerup activePowerup) {
        this.activePowerups.add(activePowerup);
    }
}
