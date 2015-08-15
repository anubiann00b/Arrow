package me.shreyasr.arrow.obstacles;

import me.shreyasr.arrow.entity.BaseEntity;

public final class PlayerObstacleCollisionDetector {

    private static final int WIDTH = 10*4;
    private static final int HEIGHT = 16*4;

    private PlayerObstacleCollisionDetector() {}

    public boolean hasCollided(BaseEntity player, Obstacle obstacle) {
        float playerLeft = player.pos.x;
        float playerBot = player.pos.y;
        float playerRight = player.pos.x + WIDTH;
        float playerTop = player.pos.y + HEIGHT;
        return playerLeft < obstacle.getPosition().x + obstacle.getWidth() &&
                playerRight > obstacle.getPosition().x &&
                playerBot < obstacle.getPosition().y + obstacle.getHeight() &&
                playerTop > obstacle.getPosition().y;
    }

}
