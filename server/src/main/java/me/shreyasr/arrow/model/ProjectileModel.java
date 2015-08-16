package me.shreyasr.arrow.model;

import me.shreyasr.arrow.model.util.Projectile;

public class ProjectileModel {

    public final int projectileID;
    private Projectile projectile;
    public final int playerID;
    private static final int LENGTH = 16*4;

    public ProjectileModel(int projectileID, int playerID,
                           Projectile projectile) {
        this.projectile = projectile;
        this.projectileID = projectileID;
        this.playerID = playerID;
    }

    public int getLength() {
        return LENGTH;
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
