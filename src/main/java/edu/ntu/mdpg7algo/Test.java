package edu.ntu.mdpg7algo;

import edu.ntu.mdpg7algo.arena.Arena;
import edu.ntu.mdpg7algo.arena.Directions;
import edu.ntu.mdpg7algo.arena.Obstacle;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
        obstacles.add(new Obstacle(0, 0, Directions.UP));
        obstacles.add(new Obstacle(0, 1, Directions.LEFT));
        obstacles.add(new Obstacle(5, 10, Directions.RIGHT));
        obstacles.add(new Obstacle(19, 19, Directions.DOWN));

        Arena arena = new Arena(obstacles);

        arena.printArena();
    }
}
