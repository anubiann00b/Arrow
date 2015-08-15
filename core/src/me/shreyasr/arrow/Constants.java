package me.shreyasr.arrow;

import com.badlogic.gdx.Gdx;

import me.shreyasr.arrow.util.CartesianPosition;

public class Constants {

//    public static final double CHAR_TO_SCREEN_SIZE_RATIO = 0.1;
    public static final CartesianPosition WORLD
            = new CartesianPosition(5000, 5000);
    public static final CartesianPosition SCREEN
            = new CartesianPosition(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

}
