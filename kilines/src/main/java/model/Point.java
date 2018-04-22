package model;

import java.util.Objects;

public class Point {

    private final Integer x;
    private final Integer y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return 117 * y + x;
    }

    @Override
    public String toString() {
        return "Pos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Point pointToCompare = (Point) obj;
        return pointToCompare.x.equals(this.x) && pointToCompare.y.equals(this.y);
    }
}
