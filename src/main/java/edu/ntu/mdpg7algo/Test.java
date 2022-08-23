package edu.ntu.mdpg7algo;

import edu.ntu.mdpg7algo.arena.Arena;
import edu.ntu.mdpg7algo.arena.Obstacle;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(0, 0, Obstacle.Dir.UP));
        obstacles.add(new Obstacle(5, 5, Obstacle.Dir.LEFT));
        obstacles.add(new Obstacle(5, 10, Obstacle.Dir.RIGHT));
        obstacles.add(new Obstacle(19, 19, Obstacle.Dir.DOWN));

        Arena arena = new Arena(obstacles);
        System.out.println(arena);
    }
}
