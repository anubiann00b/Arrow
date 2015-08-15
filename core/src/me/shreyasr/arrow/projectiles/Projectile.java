package me.shreyasr.arrow.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.util.CartesianPosition;
import me.shreyasr.arrow.util.PolarVelocity;

public class Projectile {

    //for now we can start with one type of projectile, if we get around
    //to other types we can change this into an interface or abstract class

    private final float width;
    private final float height;
    private CartesianPosition position;
    private PolarVelocity velocity;
    private final long startingTime;
    private Image image;

    public Projectile(float pWidth, float pHeight, PolarVelocity pPolarVelocity,
                      CartesianPosition pPosition, String imgFileLocation) {
        width = pWidth;
        height = pHeight;
        velocity = pPolarVelocity;
        position = pPosition;
        startingTime = System.currentTimeMillis();
        image = new Image(imgFileLocation);
    }

    public boolean update () {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - startingTime;
        position.x += velocity.magnitude*Math.cos(velocity.direction)/timePassed;
        position.y += velocity.magnitude*Math.sin(velocity.direction)/timePassed;
        return true;
    }

    public void render(SpriteBatch batch) {
        image.render(batch, position, 4.0f, (float) velocity.direction);
    }
}
