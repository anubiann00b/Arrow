package me.shreyasr.arrow.model;

public final class PlayerProjectileCollisionDetector {

    private PlayerProjectileCollisionDetector() {

    }

    public static boolean hasCollided(PlayerModel player, ProjectileModel projectile) {
        int projectileYTip = (int) (projectile.y + projectile.getCenterY() +
                projectile.getLength()/2*Math.sin(projectile.getDirection()));
        int projectileXTip = (int) (projectile.x + projectile.getCenterX() +
                projectile.getLength()/2*Math.cos(projectile.getDirection()));
        int playerLeft = player.x;
        int playerBot = player.y;
        int playerRight = player.x + player.getWidth();
        int playerTop = player.y + player.getHeight();
        return playerLeft < projectileXTip && projectileXTip < playerRight &&
                playerBot < projectileYTip && projectileYTip < playerTop;
    }

}
