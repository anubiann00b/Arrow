package me.shreyasr.arrow.input.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.input.PlayerInputMethod;
import me.shreyasr.arrow.util.CartesianPosition;

public class AndroidPlayerInputMethod extends PlayerInputMethod {

    CartesianPosition pos = new CartesianPosition();
    CartesianPosition atk = new CartesianPosition();

    Joystick moveStick;
    Joystick atkStick;

    public void init() {
        moveStick = new Joystick(
                new CartesianPosition(Gdx.graphics.getWidth()*0.25, Gdx.graphics.getHeight()*0.25),
                100, "circle", "joystick_bg");
        atkStick = new Joystick(
                new CartesianPosition(Gdx.graphics.getWidth()*0.75, Gdx.graphics.getHeight()*0.25),
                100, "circle", "joystick_bg");
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (moveStick.onDown(new CartesianPosition(x, Gdx.graphics.getHeight() - y), pointer))
            return true;
        return atkStick.onDown(new CartesianPosition(x, Gdx.graphics.getHeight() - y), pointer);
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if (moveStick.onUp(new CartesianPosition(x, Gdx.graphics.getHeight() - y), pointer))
            return true;
        return atkStick.onUp(new CartesianPosition(x, Gdx.graphics.getHeight() - y), pointer);
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        if (moveStick.onDrag(new CartesianPosition(x, Gdx.graphics.getHeight() - y), pointer))
            return true;
        return atkStick.onDrag(new CartesianPosition(x, Gdx.graphics.getHeight() - y), pointer);
    }

    @Override
    public CartesianPosition getMovement() {
        return moveStick.getStickPosition();
    }

    @Override
    public CartesianPosition getAttack() {
        return atkStick.getStickPosition();
    }
    @Override public void render(SpriteBatch batch, double delta) {
        moveStick.renderStick(batch, delta);
        atkStick.renderStick(batch, delta);
    }

    @Override
    public boolean keyDown(int keycode) { return false; }

    @Override
    public boolean keyUp(int keycode) { return false; }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
