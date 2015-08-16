package me.shreyasr.arrow.model;

import me.shreyasr.arrow.model.util.CartesianPosition;

public class ObstacleModel {

    private final int obstacleID;
    private CartesianPosition position;
    private static final int WIDTH = 16;
    private static final int HEIGHT = 32;
    private static final float SCALE = 8.0f;

    public ObstacleModel(int obstacleID, CartesianPosition position) {
        this.obstacleID = obstacleID;
        this.position = position;
    }

    public CartesianPosition getPosition() {
        return position;
    }

    public float getWidth() {
        return WIDTH*SCALE;
    }

    public float getHeight() {
        return HEIGHT*SCALE;
    }

}
