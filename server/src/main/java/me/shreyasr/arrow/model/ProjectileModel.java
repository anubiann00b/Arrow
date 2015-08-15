package me.shreyasr.arrow.model;

public class ProjectileModel {

    private final int projectileID;
    public int x;
    public int y;
    private final PlayerModel shooter;
    private final int damage;
    private float direction;
    private float speed;
    private static final int LENGTH = 16*4;
    private static final int CENTER_X = 7*4;
    private static final int CENTER_Y = 7*4;

    public ProjectileModel(int projectileID, PlayerModel shooter, int damage) {
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

    public float getDirection() {
        return direction;
    }

    public boolean update() {
        return true;
    }

    public int getDamage() {
        return damage;
    }

}
