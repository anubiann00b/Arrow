package me.shreyasr.arrow.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.entity.Player;
import me.shreyasr.arrow.util.CartesianPosition;

public abstract class PlayerInputMethod implements InputProcessor {

    protected Player player;

    public abstract CartesianPosition getMovement();
    public abstract CartesianPosition getAttack();
    public void init() {}
    public abstract void render(SpriteBatch batch, double delta);

    public void setPlayer(Player player) {
        this.player = player;
    }
}
