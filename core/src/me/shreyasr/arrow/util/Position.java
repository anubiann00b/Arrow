package me.shreyasr.arrow.util;

public class Position {

    public final float x;
    public final float y;

    public Position() {
        this(0,0);
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position scale(float a) {
        return new Position(x*a, y*a);
    }

    public Position add(float ax, float ay) {
        return new Position(x+ax, y+ay);
    }

    public Position add(Position p) {
        return new Position(x+p.x,y+p.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
