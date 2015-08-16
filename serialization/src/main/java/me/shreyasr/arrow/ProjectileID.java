package me.shreyasr.arrow;

public class ProjectileID {

    public final int playerId;
    public final int projectileId;

    public ProjectileID(int playerId, int projectileId) {
        this.playerId = playerId;
        this.projectileId = projectileId;
    }

    @Override
    public int hashCode() {
        int result = playerId;
        result = 31 * result + projectileId;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectileID that = (ProjectileID) o;

        if (playerId != that.playerId) return false;
        return projectileId == that.projectileId;

    }
}
