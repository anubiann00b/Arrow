package me.shreyasr.arrow.obstacles;

/**
 * Very simple class..
 */
public final class BoxCollisionDetector {

    private static final int WIDTH = 10*4;
    private static final int HEIGHT = 16*4;

    private BoxCollisionDetector() {}

    public boolean hasCollided(Box o1, Box o2) {
        float o1Left = o1.getPosition().x;
        float o1Right = o1.getPosition().x + o1.getWidth();
        float o1Bot = o1.getPosition().y;
        float o1Top = o1.getPosition().y + o1.getHeight();
        float o2Left = o2.getPosition().x;
        float o2Right = o2.getPosition().x + o2.getWidth();
        float o2Bot = o2.getPosition().y;
        float o2Top = o2.getPosition().y + o2.getHeight();
        return o1Left < o2Right &&
                o1Right > o2Left &&
                o1Bot < o2Top &&
                o1Top > o2Bot;
    }

}
