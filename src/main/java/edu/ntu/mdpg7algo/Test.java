package edu.ntu.mdpg7algo;

import edu.ntu.mdpg7algo.models.*;
import edu.ntu.mdpg7algo.pathfinding.HybridAStarPlanner;

import java.util.ArrayList;

/**
 * Run basic algorithm tests in console
 */
public class Test {
    public static void main(String[] args) {
//        ArrayList<Obstacle> obstacles = new ArrayList<>();
////        obstacles.add(new Obstacle(0, 0, Obstacle.Facing.UP));
////        obstacles.add(new Obstacle(5, 5, Obstacle.Facing.LEFT));
////        obstacles.add(new Obstacle(5, 10, Obstacle.Facing.RIGHT));
////        obstacles.add(new Obstacle(19, 19, Obstacle.Facing.DOWN));
//
//        Car car = new Car(new Position(107.2, 99.1, 182));
//
//        Arena arena = new Arena();
//        arena.setObstacles(obstacles);
//        arena.setCar(car);
//        System.out.println(arena);

        // Test AStar
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(0, 0, Obstacle.Facing.UP, Obstacle.ObstacleImage.A));
        obstacles.add(new Obstacle(5, 5, Obstacle.Facing.LEFT, Obstacle.ObstacleImage.A));
        obstacles.add(new Obstacle(10, 5, Obstacle.Facing.RIGHT, Obstacle.ObstacleImage.A));
        obstacles.add(new Obstacle(19, 19, Obstacle.Facing.DOWN, Obstacle.ObstacleImage.A));
        Arena arena = new Arena();
        arena.setObstacles(obstacles);

        HybridAStarPlanner hybridAStarPlanner = new HybridAStarPlanner();
        hybridAStarPlanner.setObstacles(arena.getObstaclesMatrix());
        Node2D[] nodes2D = new Node2D[400];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                nodes2D[j * 20 + i] = new Node2D(i, j, 0, 0, null);
            }
        }

        double gh = hybridAStarPlanner.aStar(
                new Node2D(3, 3, 0, 0, null),
                new Node2D(12, 12, 0, 0, null),
                nodes2D,
                20,
                20);
    }
}
