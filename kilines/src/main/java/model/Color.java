package model;

public enum Color {
    RED(255, 0, 0);

    private int r;
    private int g;
    private int b;

    Color(int r1, int g1, int b1) {
        this.r = r1;
        this.g = g1;
        this.b = b1;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }
}
