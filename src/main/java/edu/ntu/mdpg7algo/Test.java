package edu.ntu.mdpg7algo;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Car;
import edu.ntu.mdpg7algo.models.Obstacle;
import edu.ntu.mdpg7algo.models.Position;

import java.util.ArrayList;

/**
 * Run basic algorithm tests in console
 */
public class Test {
    public static void main(String[] args) {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
//        obstacles.add(new Obstacle(0, 0, Obstacle.Facing.UP));
//        obstacles.add(new Obstacle(5, 5, Obstacle.Facing.LEFT));
//        obstacles.add(new Obstacle(5, 10, Obstacle.Facing.RIGHT));
//        obstacles.add(new Obstacle(19, 19, Obstacle.Facing.DOWN));

        Car car = new Car(new Position(107.2, 99.1, 182));

        Arena arena = new Arena();
        arena.setObstacles(obstacles);
        arena.setCar(car);
        System.out.println(arena);
    }
}
