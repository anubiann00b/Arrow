package me.shreyasr.arrow.entity;

public class Player extends BaseEntity {

    public Player() {
        super("player");
    }

    @Override
    public boolean update(double delta) {
        return false;
    }
}
