package me.shreyasr.arrow.entity;

public class EnemyPlayer extends BaseEntity {

    public final int id;

    public EnemyPlayer(String name, int id) {
        super("player", name);
        this.id = id;
    }

    @Override
    public boolean update(double delta) {
        return false;
    }
}
