package me.shreyasr.arrow.obstacles;

import me.shreyasr.arrow.util.CartesianPosition;

public class Tree extends Obstacle{

    private static final String FILENAME = "tree";

    public Tree(CartesianPosition position, int startX, int startY, int width, int height) {
        super(FILENAME, position, startX, startY, width, height);
    }

}
