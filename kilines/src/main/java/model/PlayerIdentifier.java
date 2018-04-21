package model;

public class PlayerIdentifier {
    private final Color color;
    private final int playerId;

    public PlayerIdentifier(Color color, int playerId) {
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
