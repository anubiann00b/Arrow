package me.shreyasr.arrow.entity;

import java.util.List;

import me.shreyasr.arrow.entity.attack.AttackBow;
import me.shreyasr.arrow.input.PlayerInputMethod;
import me.shreyasr.arrow.CollisionDetector;
import me.shreyasr.arrow.Box;
import me.shreyasr.arrow.obstacles.Obstacle;
import me.shreyasr.arrow.util.CartesianPosition;

import static com.badlogic.gdx.math.MathUtils.random;


public class Player extends BaseEntity {

    private static final float WIDTH = 10*4;
    private static final float HEIGHT = 16*4;

    private PlayerInputMethod inputMethod;
    AttackBow attackBow;

    public Player(PlayerInputMethod inputMethod) {
        super("player", "player"+Integer.toString(random(10000)));
        this.inputMethod = inputMethod;
        attackBow = new AttackBow.Builder()
                .setFireTime(100)
                .setSpeed(8)
                .create();
    }

    @Override
    public boolean update(double delta, List<Obstacle> obstacles) {
        updatePosition(inputMethod.getMovement(), obstacles, delta);
        handleAttack(inputMethod.getAttack(), delta);
        return false;
    }

    public Box getBox() {
        return new Box(pos, WIDTH, HEIGHT);
    }

    private void updatePosition(CartesianPosition movement, List<Obstacle> obstacles, double delta) {
        CartesianPosition dpos = movement.scale((float) (delta * speed));

        CartesianPosition savedPos = pos;
        pos = pos.add(dpos);
        for (Obstacle o : obstacles) {
            if (CollisionDetector.hasCollided(this.getBox(),o.getBox())) {
                pos = savedPos;
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
        if (atkDir.isEmpty()) return;
        attackBow.update(delta, atkDir);
    }
}
