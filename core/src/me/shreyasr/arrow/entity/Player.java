package me.shreyasr.arrow.entity;

import java.util.List;

import me.shreyasr.arrow.Box;
import me.shreyasr.arrow.CollisionDetector;
import me.shreyasr.arrow.entity.attack.AttackBow;
import me.shreyasr.arrow.input.PlayerInputMethod;
import me.shreyasr.arrow.obstacles.Obstacle;
import me.shreyasr.arrow.util.CartesianPosition;

import static com.badlogic.gdx.math.MathUtils.random;


public class Player extends BaseEntity {

    private static final float WIDTH = 10*4;
    private static final float HEIGHT = 16*4;

    private PlayerInputMethod inputMethod;
    public AttackBow attackBow;
    public double powerupStartTime;

    public Player(PlayerInputMethod inputMethod) {
        super("player", "player"+Integer.toString(random(10000)));
        this.inputMethod = inputMethod;
        attackBow = new AttackBow.Builder()
                .setFireTime(100)
                .setSpeed(8)
                .create();
    }

    @Override
    public boolean update(double delta, List<Obstacle> obstacles, List<Obstacle> powerups) {
        updatePosition(inputMethod.getMovement(), obstacles, powerups, delta);
        handleAttack(inputMethod.getAttack(), delta);
        return false;
    }

    public Box getBox() {
        return new Box(pos, WIDTH, HEIGHT);
    }

    private void updatePosition(CartesianPosition movement, List<Obstacle> obstacles, List<Obstacle> powerups, double delta) {
        CartesianPosition dpos = movement.scale((float) (delta * speed));

        CartesianPosition savedPos = pos;
        pos = pos.add(dpos);
        for (Obstacle o : obstacles) {
            if (CollisionDetector.hasCollided(this.getBox(),o.getBox())) {
                pos = savedPos;
                break;
            }
        }
        for (Obstacle p : powerups) {
            if (CollisionDetector.hasCollided(this.getBox(),p.getBox())) {
                int x = p.getPowerup();
                if (x==1){
                    attackBow.setPowerup(x);
                    attackBow.setPowerupTimer(5000);
                    long t = System.currentTimeMillis();
                    attackBow.setStartTime(t);
                    attackBow.setDamage(10);
                }
                powerups.remove(p);
                break;
            }
        }

        if (dpos.x == 0 && dpos.y == 0) {
            sprite.notMoving();
        } else if (Math.abs(dpos.x) > Math.abs(dpos.y)) {
            if (dpos.x > 0)
                dir = 0;
            else
                dir = 2;
        } else {
            if (dpos.y > 0)
                dir = 1;
            else
                dir = 3;
        }
    }

    private void handleAttack(CartesianPosition atkDir, double delta) {
        attackBow.update(delta, atkDir);
    }
}
