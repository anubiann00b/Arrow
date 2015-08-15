package me.shreyasr.arrow.entity;

import me.shreyasr.arrow.input.PlayerInputMethod;
import me.shreyasr.arrow.util.CartesianPosition;

public class Player extends BaseEntity {

    private PlayerInputMethod inputMethod;

    public Player(PlayerInputMethod inputMethod) {
        super("player");
        this.inputMethod = inputMethod;
    }

    @Override
    public boolean update(double delta) {
        updatePosition(inputMethod.getMovement(), delta);

        CartesianPosition attack = inputMethod.getAttack();
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
}
