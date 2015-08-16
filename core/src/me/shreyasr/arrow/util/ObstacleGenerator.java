package me.shreyasr.arrow.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.shreyasr.arrow.CollisionDetector;
import me.shreyasr.arrow.obstacles.Obstacle;

public final class ObstacleGenerator {

    private static Random random = new Random();

    public static List<Obstacle> generate(String filename, int numObstacles, int leftBound, int rightBound,
                                int botBound, int topBound) {
        List<Obstacle> obstacles = new ArrayList<Obstacle>();
        for (int i = 0; i < numObstacles; i++) {
            boolean conflicting = true;
            while (conflicting) {
                conflicting = false;
                int x = random.nextInt(rightBound - leftBound) + leftBound;
                int y = random.nextInt(topBound - botBound) + botBound;
                Obstacle newObstacle = new Obstacle(filename, x, y);
                for (Obstacle o : obstacles) {
                    if (CollisionDetector.hasCollided(newObstacle.getBox(), o.getBox())) {
                        conflicting = true;
                        break;
                    }
                }
                if (!conflicting) {
                    obstacles.add(newObstacle);
                }
            }
        }
        return obstacles;
    }

}
