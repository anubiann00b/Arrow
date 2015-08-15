package me.shreyasr.arrow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import me.shreyasr.arrow.entity.BaseEntity;
import me.shreyasr.arrow.entity.Player;
import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.input.PlayerInputMethod;
import me.shreyasr.arrow.util.CartesianPosition;
import me.shreyasr.arrow.util.MathHelper;

public class Game extends ApplicationAdapter {

    SpriteBatch batch;
    List<BaseEntity> entities;
    PlayerInputMethod inputMethod;
    InputMultiplexer inputMultiplexer;
    OrthographicCamera camera;
    Player player;
    Image tileImage;

    public Game(PlayerInputMethod inputMethod) {
        this.inputMethod = inputMethod;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(inputMethod);
        entities = new ArrayList<BaseEntity>();
        player = new Player(inputMethod);
        entities.add(player);
        tileImage = new Image("grass");

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(inputMethod);

        camera = new OrthographicCamera(Constants.SCREEN.x*768f/Constants.SCREEN.y, 768);
    }

    @Override
    public void render() {
        double delta = 1;
        if (Gdx.graphics.getFramesPerSecond() != 0)
            delta = (double) 60/Gdx.graphics.getFramesPerSecond();

        for (BaseEntity entity : entities) {
            entity.update(delta);
        }

        updateCamera();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        renderWorld();

        for (BaseEntity entity : entities) {
            entity.render(batch, delta);
        }

        batch.end();
    }

    private void updateCamera() {
        float minX = Constants.SCREEN.x / 2;
        float maxX = Constants.WORLD.x - minX;
        float minY = Constants.SCREEN.y / 2;
        float maxY = Constants.WORLD.y - minY;
        camera.position.set(
                MathHelper.median(minX, player.pos.x, maxX),
                MathHelper.median(minY, player.pos.y, maxY),
                0);
        camera.update();
    }

    private void renderWorld() {
        float cx = camera.position.x;
        float cy = camera.position.y;

        float sx = Constants.SCREEN.x/2;
        float sy = Constants.SCREEN.y/2;

        for (int i=(int)(cx-sx)/64-2;i<(int)(cx+sx)/64+3;i++) {
            for (int j=(int)(cy-sy)/64-2;j<(int)(cy+sy)/64+3;j++) {
                tileImage.renderNoCenter(batch, new CartesianPosition(i * 64, j * 64), 4);
            }
        }
    }
}
