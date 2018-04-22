package model;

import java.util.Random;

public enum Direction {
    UP(0),
    DOWN(1),
    LEFT(2),
    RIGHT(3);

    private int dir;
    Direction(int i) {
        dir = i;
    }

    public int getDir() {
        return dir;
    }

}
