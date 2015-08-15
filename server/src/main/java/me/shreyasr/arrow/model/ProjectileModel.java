package me.shreyasr.arrow.model;

import me.shreyasr.arrow.model.util.Projectile;

public class ProjectileModel {

    private final int projectileID;
    private Projectile projectile;
    private final PlayerModel shooter;
    private final int damage;
    private static final int LENGTH = 16*4;
    private static final int CENTER_X = 7*4;
    private static final int CENTER_Y = 7*4;

    public ProjectileModel(int projectileID, PlayerModel shooter, int damage,
                           Projectile projectile) {
        this.projectile = projectile;
        this.projectileID = projectileID;
        this.shooter = shooter;
        this.damage = damage;
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

    public boolean update() {
        return true;
    }

    public int getDamage() {
        return damage;
    }

}
