package me.shreyasr.arrow;

import me.shreyasr.arrow.util.CartesianPosition;

public class Box {

    private CartesianPosition position;
    private float width;
    private float height;

    //position is the center position
    public Box(CartesianPosition position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public float getLeft() {
        return position.x - width/2;
    }

    public float getRight() {
        return position.x + width/2;
    }

    public float getTop() {
        return position.y + height/2;
    }

    public float getBot() {
        return position.y - height/2;
    }


}
