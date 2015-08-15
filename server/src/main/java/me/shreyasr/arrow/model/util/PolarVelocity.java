package me.shreyasr.arrow.model.util;

public class PolarVelocity {

    private final double direction;
    private final float speed;

    public PolarVelocity(double direction, float speed) {
        this.speed = speed;
        this.direction = direction;
    }

    public double getDirection() {
        return direction;
    }

    public float getSpeed() {
        return speed;
    }
}
