package me.shreyasr.arrow.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.util.CartesianPosition;

public class Obstacle {

    private Image image;
    private CartesianPosition position;
    private int width;
    private int height;

    public Obstacle(String filename, int x, int y) {
        this.width = 16;
        this.height = 16;
        this.image = new Image(filename);
        this.position = new CartesianPosition(x, y);
    }

    public CartesianPosition getPosition() {
        return position;
    }

    public void setPosition(CartesianPosition position) {
        this.position = position;
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
