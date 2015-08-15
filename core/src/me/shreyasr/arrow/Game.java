package me.shreyasr.arrow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import me.shreyasr.arrow.entity.BaseEntity;
import me.shreyasr.arrow.entity.Player;
import me.shreyasr.arrow.input.PlayerInputMethod;

public class Game extends ApplicationAdapter {

    SpriteBatch batch;
    List<BaseEntity> entities;
    private PlayerInputMethod inputMethod;
    InputMultiplexer inputMultiplexer;
    Player player;

    public Game(PlayerInputMethod inputMethod) {
        this.inputMethod = inputMethod;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        entities = new ArrayList<BaseEntity>();
        player = new Player(inputMethod);
        entities.add(player);

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(inputMethod);
    }

    @Override
    public void render() {
        double delta = 1;
        if (Gdx.graphics.getFramesPerSecond() != 0)
            delta = (double) 60/Gdx.graphics.getFramesPerSecond();

        for (BaseEntity entity : entities) {
            entity.update(delta);
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for (BaseEntity entity : entities) {
            entity.render(batch, delta);
        }

        batch.end();
    }
}
