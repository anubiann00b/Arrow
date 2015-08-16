package me.shreyasr.arrow.model;

public class PlayerModel {

    public final int playerId;
    private int score;
    public int health = 100;
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
        return true;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int delta) {
        this.score += delta;
    }
}
