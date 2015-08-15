package me.shreyasr.arrow.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.util.Position;

public class KeyboardPlayerInputMethod extends PlayerInputMethod {

    Position pos = new Position();

    @Override
    public Position getMovement() {
        return pos;
    }

    @Override
    public Position getAttack() {
        return null;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
                pos = pos.add(1,0);
                break;
            case Input.Keys.A:
                pos = pos.add(-1,0);
                break;
            case Input.Keys.W:
                pos = pos.add(0,1);
                break;
            case Input.Keys.S:
                pos = pos.add(0,-1);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.W)
            return keyDown(Input.Keys.S);
        else if (keycode == Input.Keys.S)
            return keyDown(Input.Keys.W);
        if (keycode == Input.Keys.A)
            return keyDown(Input.Keys.D);
        else if (keycode == Input.Keys.D)
            return keyDown(Input.Keys.A);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
    @Override public void render(SpriteBatch batch, double delta) { }
}
