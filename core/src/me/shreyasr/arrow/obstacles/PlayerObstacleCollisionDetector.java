package me.shreyasr.arrow.obstacles;

import me.shreyasr.arrow.entity.BaseEntity;

/**
 * Very simple class..
 */
public final class PlayerObstacleCollisionDetector {

    private static final int WIDTH = 10*4;
    private static final int HEIGHT = 16*4;

    private PlayerObstacleCollisionDetector() {}

    public boolean hasCollided(BaseEntity player, Obstacle obstacle) {
        float playerLeft = player.pos.x;
        float playerBot = player.pos.y;
        float playerRight = player.pos.x + WIDTH;
        float playerTop = player.pos.y + HEIGHT;
        float obstacleLeft = obstacle.getPosition().x;
        float obstacleRight = obstacle.getPosition().x + obstacle.getWidth();
        float obstacleBot = obstacle.getPosition().y;
        float obstacleTop = obstacle.getPosition().y + obstacle.getHeight();
        return playerLeft < obstacleRight &&
                playerRight > obstacleLeft &&
                playerBot < obstacleTop &&
                playerTop > obstacleBot;
    }

    public boolean hasCollided(Obstacle o1, Obstacle o2) {
        float o1Left = o1.getPosition().x;
        float o1Right = o1.getPosition().x + o1.getWidth();
        float o1Bot = o1.getPosition().y;
        float o1Top = o1.getPosition().y + o1.getHeight();
        float o2Left = o2.getPosition().x;
        float o2Right = o2.getPosition().x + o2.getWidth();
        float o2Bot = o2.getPosition().y;
        float o2Top = o2.getPosition().y + o2.getHeight();
        return o1Left < o2Right &&
                o1Right > o2Left &&
                o1Bot < o2Top &&
                o1Top > o2Bot;
    }

}
