package me.shreyasr.arrow;

import me.shreyasr.arrow.model.PlayerModel;
import me.shreyasr.arrow.model.ProjectileModel;

public final class PlayerProjectileCollisionDetector {

    private PlayerProjectileCollisionDetector() {

    }

    public static boolean hasCollided(PlayerModel player, ProjectileModel projectile) {
        int projectileYTip = (int) (projectile.getY() + projectile.getCenterY() +
                projectile.getLength()/2*Math.sin(projectile.getDirection()));
        int projectileXTip = (int) (projectile.getX() + projectile.getCenterX() +
                projectile.getLength()/2*Math.cos(projectile.getDirection()));
        int playerLeft = player.x;
        int playerBot = player.y;
        int playerRight = player.x + player.getWidth();
        int playerTop = player.y + player.getHeight();
        return playerLeft < projectileXTip && projectileXTip < playerRight &&
                playerBot < projectileYTip && projectileYTip < playerTop;
    }

}
