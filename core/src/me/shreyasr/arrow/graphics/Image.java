package me.shreyasr.arrow.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.arrow.util.Position;

/**
 * Usage:
 *
 * Image image = new Image("arrow");
 * arrow.render(params);
 */
public class Image {

    final Texture texture;
    final Pixmap pixmap;
//    public final ImageMask mask;

    public Image(String file) {
        pixmap = new Pixmap(Gdx.files.internal(file + ".png"));
        texture = new Texture(pixmap);
//        mask = new ImageMask(new TextureRegion(texture), pixmap);
    }

    public void renderNoCenter(SpriteBatch batch, Position pos, float scale) {
        batch.draw(texture, pos.x, pos.y, scale*texture.getWidth(), scale*texture.getHeight());
    }

    public void render(SpriteBatch batch, Position pos) {
        batch.draw(texture, pos.x-texture.getWidth()/2, pos.y-texture.getHeight()/2);
    }

    public void renderNoCamera(SpriteBatch batch, Position pos, float scale) {
        batch.draw(texture, pos.x-texture.getWidth()/2*scale, pos.y-texture.getHeight()/2*scale,
                scale*texture.getWidth(), scale*texture.getHeight());
    }

    public void render(SpriteBatch batch, Position pos, float scale) {
        batch.draw(texture, pos.x-texture.getWidth()/2*scale, pos.y-texture.getHeight()/2*scale,
                scale*texture.getWidth(), scale*texture.getHeight());
    }

    public void render(SpriteBatch batch, Position pos, Position scale) {
        batch.draw(texture, pos.x-texture.getWidth()/2, pos.y-texture.getHeight()/2, scale.x, scale.y);
    }

    public void render(SpriteBatch batch, Position pos, float scale, float rotation) {
        batch.draw(texture, pos.x-texture.getWidth()/2, pos.y-texture.getHeight()/2,
                texture.getWidth()/2, texture.getHeight()/2, texture.getWidth(), texture.getHeight(),
                scale, scale, (float)(rotation*180/Math.PI),
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getWidth();
    }
}
