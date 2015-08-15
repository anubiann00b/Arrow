package me.shreyasr.arrow.util;

import me.shreyasr.arrow.Constants;

public class CartesianPosition {

    public final float x;
    public final float y;

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

    public CartesianPosition subtract(int x, int y) {
        return add(-x, -y);
    }

    public CartesianPosition subtract(CartesianPosition p) {
        return new CartesianPosition(x-p.x, y-p.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public CartesianPosition invertX() {
        return new CartesianPosition(-x, y);
    }

    public boolean isEmpty() {
        return x==0 && y==0;
    }

    public double getDir() {
        return Math.atan2(y, x);
    }

    public boolean isInWorld(int padding) {
        return x>-padding && y>-padding && x< Constants.WORLD.x+padding && y<Constants.WORLD.y+padding;
    }
}
