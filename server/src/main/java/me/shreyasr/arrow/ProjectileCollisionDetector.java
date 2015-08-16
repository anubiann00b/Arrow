package me.shreyasr.arrow;

import me.shreyasr.arrow.model.ObstacleModel;
import me.shreyasr.arrow.model.PlayerModel;
import me.shreyasr.arrow.model.ProjectileModel;
import me.shreyasr.arrow.model.util.Box;
import me.shreyasr.arrow.model.util.CartesianPosition;

public final class ProjectileCollisionDetector {

    public static boolean hasCollided(PlayerModel player, ProjectileModel projectile) {
        int projectileYTip = (int) (projectile.getY()+
                projectile.getLength()/2*Math.sin(projectile.getDirection()));
        int projectileXTip = (int) (projectile.getX() +
                projectile.getLength()/2*Math.cos(projectile.getDirection()));
        Box box = new Box(new CartesianPosition(player.x, player.y), player.getWidth(),
                player.getHeight());
        float playerLeft = box.getLeft();
        float playerTop = box.getTop();
        float playerRight = box.getRight();
        float playerBot = box.getBot();
        return playerLeft <= projectileXTip && projectileXTip <= playerRight &&
                playerBot <= projectileYTip && projectileYTip <= playerTop;
    }

    public static boolean hasCollided(ObstacleModel obstacle, ProjectileModel projectile) {
        int projectileYTip = (int) (projectile.getY()+
                projectile.getLength()/2*Math.sin(projectile.getDirection()));
        int projectileXTip = (int) (projectile.getX() +
                projectile.getLength()/2*Math.cos(projectile.getDirection()));
        Box box = new Box(obstacle.getPosition(), obstacle.getWidth(),
                obstacle.getHeight());
        float obstacleLeft = box.getLeft();
        float obstacleTop = box.getTop();
        float obstacleRight = box.getRight();
        float obstacleBot = box.getBot();
        return obstacleLeft <= projectileXTip && projectileXTip <= obstacleRight &&
                obstacleBot <= projectileYTip && projectileYTip <= obstacleTop;
    }

}
