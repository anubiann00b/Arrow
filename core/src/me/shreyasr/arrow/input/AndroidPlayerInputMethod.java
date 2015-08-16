package me.shreyasr.arrow.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.Constants;
import me.shreyasr.arrow.Game;
import me.shreyasr.arrow.util.CartesianPosition;

public class AndroidPlayerInputMethod extends PlayerInputMethod {

    CartesianPosition pos = new CartesianPosition();
    CartesianPosition atk = new CartesianPosition();

    @Override
    public CartesianPosition getMovement() {
        return pos;
    }

    @Override
    public CartesianPosition getAttack() {
        return atk;
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
            case Input.Keys.ENTER:
                Game.change_name();
                break;
            case Input.Keys.TAB:
                Game.toggledisp();
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
        if (keycode == Input.Keys.TAB){
            Game.toggledisp();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        atk = player.pos
                .subtract(x, y)
                .subtract(
                        Game.getCameraPos()
                                .subtract(
                                        Constants.SCREEN
                                                .scale(1/2f)
                                )
                )
                .invertX();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        atk = new CartesianPosition();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return touchDown(screenX, screenY, pointer, -1);
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
