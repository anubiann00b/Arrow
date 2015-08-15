package me.shreyasr.arrow.model;

import me.shreyasr.arrow.model.util.Projectile;

public class ProjectileModel {

    public final int projectileID;
    private Projectile projectile;
    public final int playerID;
    private static final int LENGTH = 16*4;
    private static final int CENTER_X = 7*4;
    private static final int CENTER_Y = 7*4;

    public ProjectileModel(int projectileID, int playerID,
                           Projectile projectile) {
        this.projectile = projectile;
        this.projectileID = projectileID;
        this.playerID = playerID;
    }

    public int getLength() {
        return LENGTH;
    }

    public int getCenterX() {
        return CENTER_X;
    }

    public int getCenterY() {
        return CENTER_Y;
    }

    public void updateProjectile() {
        projectile.update();
    }

    public double getDirection() {
        return projectile.getVelocity().getDirection();
    }

    public float getSpeed() {
        return projectile.getVelocity().getSpeed();
    }

    public float getX() {
        return projectile.getPosition().x;
    }

    public float getY() {
        return projectile.getPosition().y;
    }

}
