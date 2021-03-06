package me.shreyasr.arrow.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

import me.shreyasr.arrow.Constants;
import me.shreyasr.arrow.graphics.EntitySprite;
import me.shreyasr.arrow.obstacles.Obstacle;
import me.shreyasr.arrow.util.CartesianPosition;


public abstract class BaseEntity {

    protected EntitySprite sprite;
    public int dir;
    public CartesianPosition pos;
    protected final double speed = 5;
    public String name;
    public int health;
    public int kill;
    public int death;
    BitmapFont bitmapFont;

    public BaseEntity(String filePrefix, String name) {
        sprite = new EntitySprite(filePrefix, 166, 64);
        pos = new CartesianPosition(50, 50);
        this.name = name;
        health = 100;
        kill = 0;
        death = 0;
        bitmapFont = new BitmapFont();
    }

    public abstract boolean update(double delta, List<Obstacle> obstacles, List<Obstacle> powerups);

    public void render(SpriteBatch batch, double delta, OrthographicCamera camera, boolean dispboard, int i) {
        sprite.setDirection(dir);
        sprite.render(batch, (int) (delta * 1000 / 60), pos);
        /* If you want the name at the top */
        //dispname.draw(batch, name, pos.x-sprite.getWidth()/2, pos.y + sprite.getHeight() / 2+20);
        /* If you want the name at the bottom */
        bitmapFont.draw(batch, name, pos.x - bitmapFont.getSpaceWidth() * name.length() * 2, pos.y - sprite.getHeight() / 2 - 2);
        if(i==0){
            Pixmap pixmap = new Pixmap(Gdx.files.internal("crown.png"));
            Texture crown = new Texture(pixmap);
            batch.draw(crown, pos.x-crown.getWidth()/6, pos.y+sprite.getHeight()/2, sprite.getWidth()/2, sprite.getWidth()/2);
        }
        if(dispboard){
            bitmapFont.draw(batch, "User", camera.position.x - Constants.SCREEN.x / 5, camera.position.y + Constants.SCREEN.y / 3 +15);
            bitmapFont.draw(batch, "Health", camera.position.x + Constants.SCREEN.x / 5, camera.position.y + Constants.SCREEN.y / 3 +15);
            bitmapFont.draw(batch, "Kills", camera.position.x, camera.position.y + Constants.SCREEN.y / 3 +15);
            bitmapFont.draw(batch, "Deaths", camera.position.x + Constants.SCREEN.x / 12, camera.position.y + Constants.SCREEN.y / 3 +15);
            bitmapFont.draw(batch, name, camera.position.x - Constants.SCREEN.x / 5, camera.position.y + Constants.SCREEN.y / 3 -i*15);
            bitmapFont.draw(batch, Float.toString(health), camera.position.x + Constants.SCREEN.x / 5, camera.position.y + Constants.SCREEN.y / 3 - i*15);
            bitmapFont.draw(batch, Float.toString(kill), camera.position.x, camera.position.y + Constants.SCREEN.y / 3 - i*15);
            bitmapFont.draw(batch, Float.toString(death), camera.position.x + Constants.SCREEN.x / 12, camera.position.y + Constants.SCREEN.y / 3 - i*15);
        }
    }

    public void renderstatus(ShapeRenderer healthbar) {
        healthbar.begin(ShapeRenderer.ShapeType.Line);
        healthbar.setColor(Color.GREEN);
        healthbar.line(pos.x - sprite.getWidth() / 2,
                pos.y + sprite.getHeight() / 2 + 3,
                pos.x - sprite.getWidth() / 2 + (health / 100f) * sprite.getWidth(),
                pos.y + sprite.getHeight() / 2 + 3);
        healthbar.setColor(Color.RED);
        healthbar.line(pos.x - sprite.getWidth() / 2 + health / 100f * sprite.getWidth(),
                pos.y + sprite.getHeight() / 2 + 3,
                pos.x + sprite.getWidth() / 2,
                pos.y + sprite.getHeight() / 2 + 3);
        healthbar.end();

    }
}
