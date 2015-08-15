package me.shreyasr.arrow.util;

public class CartesianPosition {

    public float x;
    public float y;

    public CartesianPosition() {
        this(0,0);
    }

    public CartesianPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public CartesianPosition scale(float a) {
        return new CartesianPosition(x*a, y*a);
    }

    public CartesianPosition add(float ax, float ay) {
        return new CartesianPosition(x+ax, y+ay);
    }

    public CartesianPosition add(CartesianPosition p) {
        return new CartesianPosition(x+p.x,y+p.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
