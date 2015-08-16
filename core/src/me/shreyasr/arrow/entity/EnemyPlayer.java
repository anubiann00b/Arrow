package me.shreyasr.arrow.entity;

import java.util.List;

import me.shreyasr.arrow.obstacles.Obstacle;

public class EnemyPlayer extends BaseEntity {

    public final int id;
    public long lastUpdated;

    public EnemyPlayer(String name, int id) {
        super("player", name);
        this.id = id;
    }

    @Override
    public boolean update(double delta, List<Obstacle> obstacles, List<Obstacle> powerups) {
        return System.currentTimeMillis() - lastUpdated > 5000;
    }
}
