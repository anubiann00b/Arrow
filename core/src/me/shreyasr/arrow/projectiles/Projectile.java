package me.shreyasr.arrow.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import me.shreyasr.arrow.Box;
import me.shreyasr.arrow.CollisionDetector;
import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.obstacles.Obstacle;
import me.shreyasr.arrow.util.CartesianPosition;
import me.shreyasr.arrow.util.PolarVelocity;

public class Projectile {

    private static final AtomicInteger counter = new AtomicInteger();

    private CartesianPosition position;
    private Image image;

    public final CartesianPosition startPos;
    public final PolarVelocity velocity;
    public final long beginningTime;
    public final int id;

    public Projectile(PolarVelocity pPolarVelocity, CartesianPosition pPosition,
                      String imgFileLocation, long startTime, int tid) {
        velocity = pPolarVelocity;
        startPos = pPosition;
        position = pPosition;
        beginningTime = startTime;
        image = new Image(imgFileLocation);
        if (tid == -1)
            id = counter.incrementAndGet();
        else
            id = tid;
    }

    public CartesianPosition getPosition() {
        return position;
    }

    public PolarVelocity getVelocity() {
        return velocity;
    }

    /** True to keep, false to leave. */
    public boolean update(List<Obstacle> obstacles) {
        long timePassed = System.currentTimeMillis() - beginningTime;
        float newX = startPos.x + (float) (velocity.getSpeed()*Math.cos(velocity.getDirection())*timePassed/100f);
        float newY = startPos.y + (float) (velocity.getSpeed()*Math.sin(velocity.getDirection())*timePassed/100f);
        position = new CartesianPosition(newX, newY);
        Box atk = new Box(position, 30, 30);
//        if (timePassed>800 && CollisionDetector.hasCollided(atk, Game.player.getBox())){
//            if(Game.player.health<Game.player.attackBow.getDamage()){
//                Game.player.health = 0;
//            } else{
//                Game.player.health -= Game.player.attackBow.getDamage();
//            }
//
//            return false;
//        }
        for (Obstacle obstacle : obstacles) {
            if (CollisionDetector.hasCollided(
                    new Box(obstacle.getPosition(), obstacle.getWidth(), obstacle.getHeight()),
                    new Box(position, 16, 16))
                    ){
                return false;
            }
        }

        return position.isInWorld(196);
    }

    public void render(SpriteBatch batch) {
        image.render(batch, position, 4.0f, (float) velocity.getDirection());
    }
}
