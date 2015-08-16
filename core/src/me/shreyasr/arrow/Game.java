package me.shreyasr.arrow;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
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
import me.shreyasr.arrow.util.PolarVelocity;

public class Game extends ApplicationAdapter {

    public static Game inst;
    BitmapFont font;
    SpriteBatch batch;
    List<BaseEntity> entities;
    private Map<ProjectileID, Projectile> projectiles = new ConcurrentHashMap<ProjectileID, Projectile>();
    public static List<Obstacle> obstacles;
    public static List<Obstacle> powerups;
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
    public static Queue<String> messageQueue = new LinkedBlockingQueue<String>();

    public Game(PlayerInputMethod inputMethod, String ip) {
        this.inputMethod = inputMethod;
        this.ip = ip;
        inst = this;
    }

    public void addProjectile(Projectile p) {
        projectiles.put(new ProjectileID(networkHandler.clientId, p.id), p);
        networkHandler.sendProjectile(p);
    }

    public static void addMessage(String s) {
        if (messageQueue.size() == 10) {
            messageQueue.poll();
        }
        messageQueue.add(s);
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
        font = new BitmapFont();
        font.setColor(Color.GREEN);

        obstacles = new ArrayList<Obstacle>();
        obstacles.addAll(ObstacleGenerator.generate("badTree", 40, 100, 5000,
                100, 5000));

        powerups = new ArrayList<Obstacle>();
        powerups.addAll(ObstacleGenerator.generate("fire_arrow", 50, 100, 5000,
                100, 5000));
        for (Obstacle p : powerups) {
            System.out.println(p.getPosition().x + " " + p.getPosition().y);
        }

        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
        inputMultiplexer.addProcessor(inputMethod);

        messageQueue.add("Welcome to Arrow!");

        camera = new OrthographicCamera(Constants.SCREEN.x*768f/Constants.SCREEN.y, 768);

        setUpNetworkHandler();
    }

    @Override
    public void render() {
        double delta = 1;
        if (Gdx.graphics.getFramesPerSecond() != 0)
            delta = (double) 60/Gdx.graphics.getFramesPerSecond();

        for (BaseEntity entity : entities) {
            entity.update(delta, obstacles, powerups);
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.update();
        }
        for (Obstacle powerup : powerups) {
            powerup.update();
        }

        for (Iterator<Projectile> iterator = projectiles.values().iterator(); iterator.hasNext(); ) {
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


        Collections.sort(entities, new Comparator<BaseEntity>() {

            public int compare(BaseEntity p1, BaseEntity p2) {
                return p2.health - p1.health;
            }
        });

        int i = 0;
        for (BaseEntity entity : entities) {
            entity.render(batch, delta, camera,dispboard , i);
            i++;
        }

        for (Obstacle o : obstacles) {
            o.render(batch);
        }
        for (Obstacle p : powerups) {
            p.render(batch);
        }

        for (Projectile p : projectiles.values()) {
            p.render(batch);
        }

        renderQueue();
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        for (BaseEntity entity : entities) {
            entity.renderstatus(shapeRenderer);
        }

        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Projectile p : projectiles.values()) {
            CartesianPosition pos = new CartesianPosition(
                    (float)(p.startPos.x + (System.currentTimeMillis()-p.beginningTime)*p.velocity.getSpeed()*Math.cos(p.velocity.getDirection())/100f),
                    (float)(p.startPos.y + (System.currentTimeMillis()-p.beginningTime)*p.velocity.getSpeed()*Math.sin(p.velocity.getDirection())/100f)
            );
            int projectileYTip = (int) (pos.y + //7*4 +
                    16*4/2*Math.sin(p.velocity.getDirection()));
            int projectileXTip = (int) (pos.x + //7*4 +
                    16*4/2*Math.cos(p.velocity.getDirection()));
            shapeRenderer.circle(projectileXTip, projectileYTip, 5);
        }
        float playerLeft = player.pos.x - 32 + 12;
        float playerBot = player.pos.y - 32;
        float playerRight = player.pos.x + 10*4 - 32 + 12;
        float playerTop = player.pos.y + 16*4 - 32;
        shapeRenderer.line(playerLeft, playerTop, playerRight, playerTop);
        sadasdhapeRenderer.line(playerLeft, playerBot, playerLeft, playerTop);
        shapeRenderer.line(playerRight, playerTop, playerRight, playerBot);
        shapeRenderer.line(playerLeft, playerBot, playerRight, playerBot);
        shapeRenderer.end();
        */
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

    private void renderQueue() {
        int i = 0;
        for (String s : messageQueue) {
            font.draw(batch, s, camera.position.x - Constants.SCREEN.x/2 - 64,
                    camera.position.y + Constants.SCREEN.y/2 + 64 - i*20);
            i++;
        }
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

    public static void change_name(){

        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                addMessage("Changed name to " + text);
                player.name = text;
                inst.networkHandler.updateName(text);

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
                    if (networkHandler.clientId == -1) return;
                    if (protectedIds.contains(playerId)) return;

                    if (playerId == networkHandler.clientId) {
                        player.health = health;
                        return;
                    }

                    boolean found = false;
                    synchronized (entities) {
                        for (BaseEntity entity : entities) {
                            if (entity instanceof EnemyPlayer && ((EnemyPlayer) entity).id == playerId) {
                                entity.health = health;
                                entity.pos = new CartesianPosition(x, y);
                                entity.dir = dir;
                                ((EnemyPlayer) entity).lastUpdated = System.currentTimeMillis();
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            protectedIds.add(playerId);
                            runnableQueue.offer(new Runnable() {
                                @Override
                                public void run() {
                                    synchronized (entities) {
                                        entities.add(new EnemyPlayer("ayyyy", playerId));
                                    }
                                    protectedIds.remove((Integer) playerId);
                                }
                            });
                        }
                    }
                }
            }, new ProjectilePacketHandler.Listener() {
                @Override
                public void onReceive(final int playerId, final int projectileId, final int x, final int y,
                                      final long startTime, final double direction, final int velocity) {
                    runnableQueue.offer(new Runnable() {
                        @Override
                        public void run() {
                            projectiles.put(new ProjectileID(playerId, projectileId),
                                    new Projectile(new PolarVelocity(direction, velocity),
                                            new CartesianPosition(x, y),
                                            "arrow", startTime, projectileId));
                        }
                    });
                }
            }, new CollisionPacketHandler.Listener() {
                    @Override
                    public void onReceive(int projectileId, int playerId, int hitPlayerId) {
                        projectiles.remove(new ProjectileID(playerId, projectileId));
                    }
            }, new NameChangePacketHandler.Listener() {
                @Override
                public void onReceive(final int playerId, final String name) {
                    runnableQueue.offer(new Runnable() {
                        @Override
                        public void run() {
                            for (BaseEntity entity : entities) {
                                if (entity instanceof EnemyPlayer && ((EnemyPlayer)entity).id == playerId)
                                    entity.name = name;
                            }
                        }
                    });
                }
            }, new DeathPacketHandler.Listener() {
                @Override
                public void onReceive(final int killerId, final int victimId) {
                    String victim = entities.get(victimId).name;
                    String killer = entities.get(killerId).name;
                    addMessage(victim + " has been slain by" + killer);
                    if (networkHandler.clientId == victimId) {
                        runnableQueue.offer(new Runnable() {
                            @Override
                            public void run() {
                                entities.get(victimId).death++;
                                entities.remove(victimId);
                            }
                        });
                    }

                    runnableQueue.offer(new Runnable() {
                        @Override
                        public void run() {
                            entities.get(victimId).death++;
                            entities.get(killerId).kill++;
                            entities.remove(victimId);
                        }
                    });
                }
            }
        );
        new Thread(networkHandler).start();
    }
}
