package me.shreyasr.arrow.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.util.CartesianPosition;

public class Obstacle {

    private Image image;
    private CartesianPosition position;

    public Obstacle(String imgFileLocation, CartesianPosition oPosition) {
        image = new Image(imgFileLocation);
        position = oPosition;
    }

    private CartesianPosition getPosition() {
        return position;
    }

    public boolean update() {
        return true;
    }

    public void render(SpriteBatch batch) {
        image.render(batch, position);
    }
}
