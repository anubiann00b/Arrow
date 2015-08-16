package me.shreyasr.arrow.util;

import me.shreyasr.arrow.Constants;

public class CartesianPosition {

    public final float x;
    public final float y;
    float maxX = 5060;
    float maxY = 5020;
    float minX = -60;
    float minY = -20;

    public CartesianPosition() {
        this(0,0);
    }

    public CartesianPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public CartesianPosition scale(float a) {
        return withinBounds(x*a, y*a);
    }

    public CartesianPosition add(float ax, float ay) {
        return withinBounds(x+ax, y+ay);
    }

    public CartesianPosition add(CartesianPosition p) {
        return withinBounds(x+p.x,y+p.y);
    }

    public CartesianPosition subtract(int x, int y) {
        return add(-x, -y);
    }

    public CartesianPosition subtract(CartesianPosition p) {
        return withinBounds(x-p.x, y-p.y);
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

    private CartesianPosition withinBounds(float newX, float newY){
        if (newX>=minX){
            if(newY>=minY){
                if(newX<=maxX){
                    if(newY<maxY){
                        return new CartesianPosition(newX, newY);
                    }
                    return new CartesianPosition(newX, maxY);
                }
                else if (newY<=maxY){
                    return new CartesianPosition(maxX, newY);
                }
                return new CartesianPosition(maxX, maxY);
            }
            else if (newX<=maxX){
                return new CartesianPosition(newX, minY);
            }
            return new CartesianPosition(maxX, minY);
        }
        if(newY>=minY){
            if(newY<=maxY) {
                return new CartesianPosition(minX, newY);
            }
            return new CartesianPosition(minX, maxY);
        }
        return new CartesianPosition(minX,minY);
    }
}
