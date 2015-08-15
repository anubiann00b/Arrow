package me.shreyasr.arrow.model;

public class PlayerModel {

    public final int playerId;
    public int health;
    public int x;
    public int y;
    public int direction;

    public PlayerModel(int playerId) {
        this.playerId = playerId;
    }
}
