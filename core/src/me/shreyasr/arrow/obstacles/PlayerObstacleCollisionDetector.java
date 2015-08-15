package me.shreyasr.arrow.obstacles;

import me.shreyasr.arrow.entity.BaseEntity;

public final class PlayerObstacleCollisionDetector {

    private PlayerObstacleCollisionDetector() {}

    public boolean hasCollided(BaseEntity player, Obstacle obstacle) {
//        float playerLeft = player.pos.x;
//        float playerBot = player.pos.y;
//        float playerRight = player.pos. + player.getWidth();
//        float playerTop = player.y + player.getHeight();
//        return playerLeft < projectileXTip && projectileXTip < playerRight &&
//                playerBot < projectileYTip && projectileYTip < playerTop;
        return false;
    }

}
