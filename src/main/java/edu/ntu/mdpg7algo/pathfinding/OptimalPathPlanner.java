package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Path;
import edu.ntu.mdpg7algo.models.Position;

public class OptimalPathPlanner {
    private final String[] moveTypes = new String[]
    {
        "S", "R", "L",
        "SR", "SL", "RL", "LR", "LS", "RS",
        "RSR", "LSL", "LSR", "RSL", "LRL", "RLR"
    };

    public Path getOptimalPath(Position start, Position end){
        Path shortestPath = new Path();
        double minDist = Double.MAX_VALUE;
        for(String pathType : moveTypes){
            Path tempPath = computeShortestPath(start, end, pathType);
            if( tempPath.getTotalDistance() < minDist){
                shortestPath = tempPath;
                minDist = shortestPath.getTotalDistance();
            }

        }

        return shortestPath;

    }

    private Path computeShortestPath(Position start, Position end, String pathType){
        switch (pathType){
            case "S":

        }


        return new Path();
    }


    private Path computeStraightPath(Position start, Position end){

    }

    private Path computeLeftPath(Position start, Position end){

    }

    private Path computeRightPath(Position start, Position end){

    }




}
