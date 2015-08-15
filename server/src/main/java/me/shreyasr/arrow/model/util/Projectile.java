package me.shreyasr.arrow.model.util;

public class Projectile {

    private CartesianPosition position;
    private CartesianPosition startPos;
    private PolarVelocity velocity;
    private long beginningTime;

    public Projectile(PolarVelocity pPolarVelocity, CartesianPosition pPosition) {
        velocity = pPolarVelocity;
        startPos = pPosition;
        position = pPosition;
        beginningTime = System.currentTimeMillis();
    }

    public CartesianPosition getPosition() {
        return position;
    }

    public PolarVelocity getVelocity() {
        return velocity;
    }

    /** True to keep, false to leave. */
    public boolean update() {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - beginningTime;
        float newX = startPos.x + (float) (velocity.getSpeed()*Math.cos(velocity.getDirection())*timePassed/100f);
        float newY = startPos.y + (float) (velocity.getSpeed()*Math.sin(velocity.getDirection())*timePassed/100f);
        position = new CartesianPosition(newX, newY);
        return position.isInWorld(196);
    }
}
