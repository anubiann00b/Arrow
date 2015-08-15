package me.shreyasr.arrow.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.util.CartesianPosition;

public class Obstacle {

    private Image image;
    private CartesianPosition position;
    private final int width;
    private final int height;

    public Obstacle(String imgFileLocation, CartesianPosition oPosition, int width,
                    int height) {
        image = new Image(imgFileLocation);
        position = oPosition;
        this.width = width;
        this.height = height;
    }

    public CartesianPosition getPosition() {
        return position;
    }

    public int getWidth(){
        return  width;
    }

    public int getHeight() {
        return height;
    }

    public boolean update() {
        return true;
    }

    public void render(SpriteBatch batch) {
        image.render(batch, position);
    }
}
