package me.shreyasr.arrow;

import me.shreyasr.arrow.model.ObstacleModel;
import me.shreyasr.arrow.model.PlayerModel;
import me.shreyasr.arrow.model.ProjectileModel;
import me.shreyasr.arrow.model.util.Box;
import me.shreyasr.arrow.model.util.CartesianPosition;

public final class ProjectileCollisionDetector {

    public static boolean hasCollided(PlayerModel player, ProjectileModel projectile) {
        CartesianPosition pos = projectile.calculatePosition(System.currentTimeMillis());

        int projectileYTip = (int) (pos.y +
                projectile.getLength()/2*Math.sin(projectile.velocity.getDirection()));
        int projectileXTip = (int) (pos.x +
                projectile.getLength()/2*Math.cos(projectile.velocity.getDirection()));
        int playerLeft = player.x - 32 + 12;
        int playerBot = player.y - 32;
        int playerRight = player.x + 10*4 - 32 + 12;
        int playerTop = player.y + 16*4 - 32;
        return playerLeft < projectileXTip && projectileXTip < playerRight &&
                playerBot < projectileYTip && projectileYTip < playerTop;
    }

    public static boolean hasCollided(ObstacleModel obstacle, ProjectileModel projectile) {
        CartesianPosition pos = projectile.calculatePosition(System.currentTimeMillis());

        int projectileYTip = (int) (pos.y +
                projectile.getLength()/2*Math.sin(projectile.velocity.getDirection()));
        int projectileXTip = (int) (pos.x +
                projectile.getLength()/2*Math.cos(projectile.velocity.getDirection()));

        Box box = new Box(obstacle.getPosition(), obstacle.getWidth(), obstacle.getHeight());
        float obstacleLeft = box.getLeft();
        float obstacleTop = box.getTop();
        float obstacleRight = box.getRight();
        float obstacleBot = box.getBot();
        return obstacleLeft <= projectileXTip && projectileXTip <= obstacleRight &&
                obstacleBot <= projectileYTip && projectileYTip <= obstacleTop;
    }

}
