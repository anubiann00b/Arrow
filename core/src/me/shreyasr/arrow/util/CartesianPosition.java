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

    public CartesianPosition(double x, double y) {
        this((float)x, (float)y);
    }

    public CartesianPosition scale(float a) {
        return withinBounds(x*a, y*a);
    }

    public CartesianPosition scaleNoBounds(float a) {
        return new CartesianPosition(x*a, y*a);
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
        return withinBounds(x - p.x, y - p.y);
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

    public CartesianPosition limit(int dist, CartesianPosition pos) {
        if (inRange(dist, pos))
            return pos;

        CartesianPosition offset = new CartesianPosition(x-pos.x, y-pos.y);
        float distance = (float)Math.sqrt(offset.x*offset.x + offset.y*offset.y);
        return this.subtract(offset.scaleNoBounds(dist / distance));
    }

    public boolean inRange(int dist, CartesianPosition pos) {
        return getDistance(pos) < dist;
    }

    private float getDistance(CartesianPosition pos) {
        return (float) Math.sqrt((pos.x-x)*(pos.x-x) + (pos.y-y)*(pos.y-y));
    }
}
