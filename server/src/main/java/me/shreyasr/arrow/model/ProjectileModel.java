package me.shreyasr.arrow.model;

import me.shreyasr.arrow.model.util.CartesianPosition;
import me.shreyasr.arrow.model.util.PolarVelocity;

public class ProjectileModel {

    private static final int LENGTH = 16*4;

    public final int projectileID;
    public final int playerID;
    public CartesianPosition startPos;
    public PolarVelocity velocity;
    public long beginningTime;

    public ProjectileModel(int projectileID, int playerID, long beginningTime,
                           PolarVelocity velocity, CartesianPosition startPos) {
        this.velocity = velocity;
        this.startPos = startPos;
        this.beginningTime = beginningTime;
        this.projectileID = projectileID;
        this.playerID = playerID;
    }

    public int getLength() {
        return LENGTH;
    }

    public CartesianPosition calculatePosition(long time) {
        return new CartesianPosition(
                (float)(startPos.x + (time-beginningTime)*velocity.getSpeed()*Math.cos(velocity.getDirection())/100f),
                (float)(startPos.y + (time-beginningTime)*velocity.getSpeed()*Math.sin(velocity.getDirection())/100f)
        );
    }
}
