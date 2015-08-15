package me.shreyasr.arrow.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.shreyasr.arrow.graphics.EntitySprite;
import me.shreyasr.arrow.util.CartesianPosition;


public abstract class BaseEntity {

    protected EntitySprite sprite;
    public int dir;
    public CartesianPosition pos;
    protected final double speed = 5;
    String name;
    public int health;
    ShapeRenderer healthbar;
    BitmapFont dispname;

    public BaseEntity(String filePrefix, String name) {
        sprite = new EntitySprite(filePrefix, 166, 64);
        pos = new CartesianPosition(50, 50);
        this.name = name;
        health = 80;
        dispname = new BitmapFont();
    }

    public abstract boolean update(double delta);

    public void render(SpriteBatch batch, double delta) {
        sprite.setDirection(dir);
        sprite.render(batch, (int) (delta * 1000 / 60), pos);
        /* If you want the name at the top */
        //dispname.draw(batch, name, pos.x-sprite.getWidth()/2, pos.y + sprite.getHeight() / 2+20);
        /* If you want the name at the bottom */
        dispname.draw(batch, name, pos.x-dispname.getSpaceWidth()*name.length()*2 , pos.y - sprite.getHeight() / 2 - 2);
    }

    public void renderstatus(ShapeRenderer healthbar){
        // does not reflect actual health atm
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
