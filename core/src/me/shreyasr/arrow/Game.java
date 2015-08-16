package me.shreyasr.arrow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import me.shreyasr.arrow.entity.BaseEntity;
import me.shreyasr.arrow.entity.EnemyPlayer;
import me.shreyasr.arrow.entity.Player;
import me.shreyasr.arrow.graphics.Image;
import me.shreyasr.arrow.input.PlayerInputMethod;
import me.shreyasr.arrow.network.NetworkHandler;
import me.shreyasr.arrow.obstacles.Obstacle;
import me.shreyasr.arrow.projectiles.Projectile;
import me.shreyasr.arrow.util.CartesianPosition;
import me.shreyasr.arrow.util.MathHelper;
import me.shreyasr.arrow.util.ObstacleGenerator;

public class Game extends ApplicationAdapter {

    BitmapFont font;
    SpriteBatch batch;
    List<BaseEntity> entities;
    public static List<Projectile> projectiles = new ArrayList<Projectile>();
    public static List<Obstacle> obstacles;
    PlayerInputMethod inputMethod;
    InputMultiplexer inputMultiplexer;
    public static OrthographicCamera camera;
    public static Player player;
    Image tileImage;
    ShapeRenderer shapeRenderer;
    NetworkHandler networkHandler;
    final String ip;
    Queue<Runnable> runnableQueue = new LinkedBlockingQueue<Runnable>();
    static boolean dispboard = false;

    public Game(PlayerInputMethod inputMethod, String ip) {
        this.inputMethod = inputMethod;
        this.ip = ip;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        player = new Player(inputMethod);
        entities = new ArrayList<BaseEntity>();
        shapeRenderer = new ShapeRenderer();
        entities.add(player);
        inputMethod.setPlayer(player);
        tileImage = new Image("grass");

        obstacles = new ArrayList<Obstacle>();
        obstacles.addAll(ObstacleGenerator.generate("badTree", 50, 100, 5000,
                100, 5000));
        for (Obstacle o : obstacles) {
            System.out.println(o.getPosition().x + " " + o.getPosition().y);
        }

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(inputMethod);

        camera = new OrthographicCamera(Constants.SCREEN.x*768f/Constants.SCREEN.y, 768);

        setUpNetworkHandler();
    }

    @Override
    public void render() {
        double delta = 1;
        if (Gdx.graphics.getFramesPerSecond() != 0)
            delta = (double) 60/Gdx.graphics.getFramesPerSecond();

        for (BaseEntity entity : entities) {
            entity.update(delta, obstacles);
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }

        for (Iterator<Projectile> iterator = projectiles.iterator(); iterator.hasNext(); ) {
            Projectile p = iterator.next();
            boolean keep = p.update(obstacles);
            if (!keep) iterator.remove();
        }

        networkHandler.update(player); //QUEUE SHIT
        while(runnableQueue.peek() != null) runnableQueue.remove().run();

        updateCamera();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        renderWorld();
        int i = 0;
        for (BaseEntity entity : entities) {
            entity.render(batch, delta, camera,dispboard , i);
            i++;
        }

        for (Obstacle o : obstacles) {
            o.render(batch);
        }

        for (Projectile p : projectiles) {
            p.render(batch);
        }

        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        for (BaseEntity entity : entities) {
            entity.renderstatus(shapeRenderer);
        }

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

    /* Doesn't work properly at the moment... */
    public static void change_name(){
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                player.name = text;
            }

            @Override
            public void canceled() {

            }
        }, "New player name?", player.name, "");
    }

    public static CartesianPosition getCameraPos() {
        return new CartesianPosition(camera.position.x, camera.position.y);
    }

    List<Integer> protectedIds = new ArrayList<Integer>();

    public static void toggledisp(){
        if (dispboard) dispboard=false;
        else dispboard=true;
    }

    private void setUpNetworkHandler() {
        networkHandler = new NetworkHandler(ip,
                new PlayerPacketHandler.Listener() {
                    @Override
                    public void onReceive(final int playerId, int health,
                                          final int x, final int y, int dir) {
                        if (playerId == networkHandler.clientId || networkHandler.clientId == -1) return;
                        if (protectedIds.contains(playerId)) return;

                        boolean found = false;
                        synchronized (entities) {
                            for (BaseEntity entity : entities) {
                                if (entity instanceof EnemyPlayer && ((EnemyPlayer) entity).id == playerId) {
                                    entity.health = health;
                                    entity.pos = new CartesianPosition(x, y);
                                    entity.dir = dir;
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                protectedIds.add(playerId);
                                runnableQueue.offer(new Runnable() {
                                    @Override
                                    public void run() {
                                        entities.add(new EnemyPlayer("ayyyy", playerId));
                                        protectedIds.remove((Integer)playerId);
                                    }
                                });
                            }
                        }
                    }
                });
        new Thread(networkHandler).start();
    }
}
