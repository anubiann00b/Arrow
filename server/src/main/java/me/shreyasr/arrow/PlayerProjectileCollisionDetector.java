package me.shreyasr.arrow;

import me.shreyasr.arrow.model.PlayerModel;
import me.shreyasr.arrow.model.ProjectileModel;
import me.shreyasr.arrow.model.util.CartesianPosition;

public final class PlayerProjectileCollisionDetector {

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
}