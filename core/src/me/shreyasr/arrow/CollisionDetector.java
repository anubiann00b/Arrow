package me.shreyasr.arrow;

/**
 * Very simple class..
 */
public final class CollisionDetector {

    public static boolean hasCollided(Box box1, Box box2) {
        return box1.getLeft() <= box2.getRight() &&
                box1.getRight() >= box2.getLeft() &&
                box1.getBot() <= box2.getTop() &&
                box1.getTop() >= box2.getBot();
    }

}