package me.shreyasr.arrow.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import me.shreyasr.arrow.Game;
import me.shreyasr.arrow.input.KeyboardPlayerInputMethod;

public class DesktopLauncher {

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 800;
        config.height = 600;
        String ip = args.length == 0 ? "54.67.114.47" : "127.0.0.1";
        new LwjglApplication(new Game(new KeyboardPlayerInputMethod(), ip), config);
    }
}
