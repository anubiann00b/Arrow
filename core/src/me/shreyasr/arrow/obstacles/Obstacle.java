package me.shreyasr.arrow.obstacles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.util.CartesianPosition;

public class Obstacle {

    private Image image;
    private CartesianPosition position;
    private float width;
    private float height;
    private static final float SCALE = 8.0f;
    private int powerup;

    public Obstacle(String filename, int x, int y) {
        //hardcoded for badTree.png size at the moment
        this.width = 16*SCALE;
        this.height = 32*SCALE;
        this.image = new Image(filename);
        if(filename.compareTo("fire_arrow")==0){
            powerup=1;
        }
        this.position = new CartesianPosition(x, y);
    }

    public me.shreyasr.arrow.Box getBox() {
        return new me.shreyasr.arrow.Box(position, width, height);
    }

    public CartesianPosition getPosition() {
        return position;
    }

    public void setPosition(CartesianPosition position) {
        this.position = position;
    }

    public float getWidth(){
        return  width;
    }

    public float getHeight() {
        return height;
    }

    public int getPowerup() { return powerup; }

    public boolean update() {
        return true;
    }

    public void render(SpriteBatch batch) {
        image.render(batch, position, SCALE);
    }
}
