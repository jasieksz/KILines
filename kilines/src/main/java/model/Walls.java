package model;

public class Walls {
    private final Color color;
    private final int playerId;

    public Walls(Color color, int playerId) {
        this.color = color;
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Color getColor() {
        return color;
    }
}
