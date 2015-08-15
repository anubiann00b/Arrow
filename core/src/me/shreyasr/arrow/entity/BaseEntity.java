package me.shreyasr.arrow.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.graphics.EntitySprite;
import me.shreyasr.arrow.util.CartesianPosition;

public abstract class BaseEntity {

    private EntitySprite sprite;
    public CartesianPosition pos;

    public BaseEntity(String filePrefix) {
        sprite = new EntitySprite(filePrefix, 166, 64);
        pos = new CartesianPosition(50, 50);
    }

    public abstract boolean update(double delta);

    public void render(SpriteBatch batch, double delta) {
        sprite.render(batch, (int)(delta*1000/60), pos);
    }
}
