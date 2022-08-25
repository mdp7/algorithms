package edu.ntu.mdpg7algo;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Obstacle;
import edu.ntu.mdpg7algo.models.Position;
import edu.ntu.mdpg7algo.models.Robot;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(0, 0, Obstacle.Dir.UP));
        obstacles.add(new Obstacle(5, 5, Obstacle.Dir.LEFT));
        obstacles.add(new Obstacle(5, 10, Obstacle.Dir.RIGHT));
        obstacles.add(new Obstacle(19, 19, Obstacle.Dir.DOWN));
        
        Robot robot = new Robot(new Position(107.2, 99.1, 182));
        
        Arena arena = new Arena();
        arena.setObstacles(obstacles);
        arena.setRobot(robot);
        System.out.println(arena);
    }
}
