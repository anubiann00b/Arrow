package me.shreyasr.arrow.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.shreyasr.arrow.obstacles.CollisionDetector;
import me.shreyasr.arrow.obstacles.Obstacle;

public final class ObstacleGenerator {

    private static Random random = new Random();
    private static me.shreyasr.arrow.obstacles.CollisionDetector CollisionDetector = new CollisionDetector();

    private ObstacleGenerator() {

    }

    public static void generate(String filename, int numObstacles, int leftBound, int rightBound,
                                int botBound, int topBound) {
        List<Obstacle> obstacles = new ArrayList<Obstacle>();
        for (int i = 0; i < numObstacles; i++) {
            boolean conflicting = false;
            while (conflicting) {
                conflicting = false;
                int x = random.nextInt(rightBound - leftBound) + leftBound;
                int y = random.nextInt(topBound - botBound) + topBound;
                Obstacle newObstacle = new Obstacle(filename, x, y);
                for (Obstacle o : obstacles) {
                    if (CollisionDetector.hasCollided(newObstacle, o)) {
                        conflicting = true;
                        break;
                    }
                }
                if (!conflicting) {
                    obstacles.add(newObstacle);
                }
            }
        }
    }

}
