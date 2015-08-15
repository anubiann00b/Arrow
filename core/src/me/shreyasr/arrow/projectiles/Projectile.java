package me.shreyasr.arrow.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.util.CartesianPosition;
import me.shreyasr.arrow.util.PolarVelocity;

public class Projectile {

    //for now we can start with one type of projectile, if we get around
    //to other types we can change this into an interface or abstract class

//    private final float width;
//    private final float height;
    private CartesianPosition position;
    private PolarVelocity velocity;
    private  long beginningTime;
    private Image image;

    public Projectile(PolarVelocity pPolarVelocity, CartesianPosition pPosition,
                      String imgFileLocation) {
        velocity = pPolarVelocity;
        position = pPosition;
        beginningTime = System.currentTimeMillis();
        image = new Image(imgFileLocation);
    }

    public CartesianPosition getPosition() {
        return position;
    }

    public PolarVelocity getVelocity() {
        return velocity;
    }

    public boolean update () {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - beginningTime;
        beginningTime = currentTime;
        if (timePassed <= 0) return false;
        float newX = position.x + (float) (velocity.speed*Math.cos(velocity.direction)/timePassed);
        float newY = position.y + (float) (velocity.speed*Math.sin(velocity.direction)/timePassed);
        position = new CartesianPosition(newX, newY);
        return true;
    }

    public void render(SpriteBatch batch) {
        image.render(batch, position, 4.0f, (float) velocity.direction);
    }
}
