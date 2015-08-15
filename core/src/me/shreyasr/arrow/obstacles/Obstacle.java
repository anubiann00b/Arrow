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

    public boolean update() {
        return false;
    }

    public void render(SpriteBatch batch) {
        image.render(batch, position);
    }
}
