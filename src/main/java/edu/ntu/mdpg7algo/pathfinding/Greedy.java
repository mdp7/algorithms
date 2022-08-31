package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Move;
import edu.ntu.mdpg7algo.models.Obstacle;
import edu.ntu.mdpg7algo.models.Path;

import java.util.ArrayList;

public class Greedy {
    private final Arena arena;
    private final OptimalPathPlanner optimalPathPlanner;
    //    possiblePaths - paths robot can traverse to different destination
    private final ArrayList<Path> possiblePaths;

    public Greedy(Arena arena) {
        this.arena = arena;
        this.optimalPathPlanner = new OptimalPathPlanner();
        this.possiblePaths = new ArrayList<Path>();
    }

    private void computePossiblePaths() {
        ArrayList<Obstacle> obstacles = arena.getObstacles();
        for (Obstacle o : obstacles) {
            if (!o.isDetected()) {
                possiblePaths.add(optimalPathPlanner.getOptimalPath(arena.getCar().getPosition(), o.computeRobotPosition()));
            }
        }
    }

    public ArrayList<Move> getOptimalMoves() {
        ArrayList<Move> shortestMoves = new ArrayList<Move>();
        double minDistance = Double.MAX_VALUE;

//        Check through all paths robot can make to get to its destination
        for (Path path : possiblePaths) {
//            Calculate distance of this path
            if (path.getTotalDistance() < minDistance) {
                minDistance = path.getTotalDistance();
                shortestMoves = path.getMoves();
            }
        }

        return shortestMoves;

    }

}
