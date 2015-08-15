package me.shreyasr.arrow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import me.shreyasr.arrow.entity.BaseEntity;
import me.shreyasr.arrow.entity.Player;

public class Game extends ApplicationAdapter {

    SpriteBatch batch;
    List<BaseEntity> entities;
    OrthographicCamera camera;
    Player player;

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player();
        entities = new ArrayList<BaseEntity>();
        entities.add(player);
        camera = new OrthographicCamera(100, 100);
    }

    @Override
    public void render() {
        float x1 = player.pos.x;
        float y1 = player.pos.y;

        double delta = 1;
        if (Gdx.graphics.getFramesPerSecond() != 0)
            delta = (double) 60/Gdx.graphics.getFramesPerSecond();

        for (BaseEntity entity : entities) {
            entity.update(delta);
        }

        camera.translate(player.pos.x - x1, player.pos.y - y1);
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        for (BaseEntity entity : entities) {
            entity.render(batch, delta);
        }

        batch.end();
    }
}
