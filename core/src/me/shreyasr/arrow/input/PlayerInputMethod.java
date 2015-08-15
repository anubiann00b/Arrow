package me.shreyasr.arrow.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.util.CartesianPosition;

public abstract class PlayerInputMethod implements InputProcessor {

    public abstract CartesianPosition getMovement();
    public abstract CartesianPosition getAttack();
    public abstract void render(SpriteBatch batch, double delta);
}
