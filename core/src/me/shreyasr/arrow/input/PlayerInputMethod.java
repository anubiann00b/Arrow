package me.shreyasr.arrow.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.util.Position;

public abstract class PlayerInputMethod implements InputProcessor {

    public abstract Position getMovement();
    public abstract Position getAttack();
    public abstract void render(SpriteBatch batch, double delta);
}
