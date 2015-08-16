package me.shreyasr.arrow.input.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.util.CartesianPosition;

public class Joystick {

    private boolean captured;
    private final int radius;
    private final CartesianPosition center;
    private CartesianPosition position;
    private int lastPointer;
    private final Image stick;
    private final Image bg;

    Joystick(CartesianPosition pos, int size, String stickImg, String bgImg) {
        center = pos;
        position = pos;
        radius = size;

        stick = new Image(stickImg);
        bg = new Image(bgImg);
    }

    public CartesianPosition getStickPosition() {
        return position.subtract(center).scale(1f/radius);
    }

    public boolean onDown(CartesianPosition pos, int pointer) {
        if (center.inRange(radius, pos)) {
            captured = true;
            position = pos;
            lastPointer = pointer;
            return true;
        }
        return false;
    }

    public boolean onDrag(CartesianPosition pos, int pointer) {
        if (captured && lastPointer == pointer) {
            lastPointer = pointer;
            position = center.limit(radius, pos);
            return true;
        }
        return false;
    }

    public boolean onUp(CartesianPosition pos, int pointer) {
        if (captured && lastPointer == pointer) {
            captured = false;
            position = center;
            lastPointer = pointer;
            return true;
        }
        return false;
    }

    void renderStick(SpriteBatch batch, double delta) {
        bg.renderNoCamera(batch, center, radius / bg.getWidth());
        stick.renderNoCamera(batch, position, 4);
    }
}