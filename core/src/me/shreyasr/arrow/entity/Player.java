package me.shreyasr.arrow.entity;

import me.shreyasr.arrow.entity.attack.AttackBow;
import me.shreyasr.arrow.input.PlayerInputMethod;
import me.shreyasr.arrow.util.CartesianPosition;

public class Player extends BaseEntity {

    private PlayerInputMethod inputMethod;
    AttackBow attackBow;

    public Player(PlayerInputMethod inputMethod) {
        super("player");
        this.inputMethod = inputMethod;
        attackBow = new AttackBow.Builder()
                .setFireTime(100)
                .setSpeed(8)
                .create();
    }

    @Override
    public boolean update(double delta) {
        updatePosition(inputMethod.getMovement(), delta);
        handleAttack(inputMethod.getAttack(), delta);
        return false;
    }

    private void updatePosition(CartesianPosition movement, double delta) {
        CartesianPosition dpos = movement.scale((float)(delta*speed));

        pos = pos.add(dpos);

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
