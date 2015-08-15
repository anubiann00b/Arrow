package me.shreyasr.arrow.model;

public class PlayerModel {

    public final int playerId;
    public int health;
    public int x;
    public int y;
    public int direction;
    private static final int WIDTH = 10*4;
    private static final int HEIGHT = 16*4;

    public PlayerModel(int playerId) {
        this.playerId = playerId;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public boolean update() {
        return false;
    }
}
