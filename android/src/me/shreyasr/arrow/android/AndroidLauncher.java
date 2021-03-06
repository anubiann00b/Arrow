package me.shreyasr.arrow.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import me.shreyasr.arrow.Game;
import me.shreyasr.arrow.input.android.AndroidPlayerInputMethod;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Game(new AndroidPlayerInputMethod(), "52.8.127.253"), config);
    }
}
