package me.shreyasr.arrow.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.shreyasr.arrow.Game;
import me.shreyasr.arrow.input.KeyboardPlayerInputMethod;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new Game(new KeyboardPlayerInputMethod()), config);
    }
}
