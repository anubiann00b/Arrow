package me.shreyasr.arrow.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.EntitySprite;
import me.shreyasr.arrow.util.Position;

public abstract class BaseEntity {

    protected EntitySprite sprite;
    protected int dir;
    public Position pos;
    protected final double speed = 5;

    public BaseEntity(String filePrefix) {
        sprite = new EntitySprite(filePrefix, 166, 64);
        pos = new Position(50, 50);
    }

    public abstract boolean update(double delta);

    public void render(SpriteBatch batch, double delta) {
        sprite.setDirection(dir);
        sprite.render(batch, (int)(delta*1000/60), pos);
    }
}
