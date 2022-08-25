package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Move;
import edu.ntu.mdpg7algo.models.Path;
import edu.ntu.mdpg7algo.models.Position;
import edu.ntu.mdpg7algo.models.Vector;

import java.util.ArrayList;

public class OptimalPathPlanner {
//    radius from centre  of robot to imaginary circle
    public static double RADIUS = 3.5;
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
//        Check if start and end theta is diff. If diff, then it is not possible
//        Hm..In real word, it may not need to be exactly same theta for straight line to be the best move. Need a range of theta
        if(start.getTheta() != end.getTheta()) return null;

        double distance = calculateStraightDistance(start, end);

        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move(Move.Dir.S, distance));
        return new Path(moves);
    }

    private Path computeLeftPath(Position start, Position end){
//        Check if possible by seeing if end point is a distance 'radius' away from centre  of circle
        Vector circleCentre = findCentreLeft(start);
//        Might need a range agn instead of exactly matching
        if(calculateStraightDistance(end, circleCentre) != RADIUS) return null;

//        Determine angle
        Vector circleToStart = new Vector(start.getX()-circleCentre.getX(), start.getY()-circleCentre.getY());
        Vector circleToEnd = new Vector(start.getX()-circleCentre.getX(), end.getY()-circleCentre.getY());
        double angle = calculateAngle(circleToStart, circleToEnd);
        if(angle < 0) angle += 2 * Math.PI;

        double arcLen = calculateArcLength(angle);
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move(Move.Dir.L, arcLen));
        return new Path(moves);
    }

    private Path computeRightPath(Position start, Position end){
//        Check if possible by seeing if end point is a distance 'radius' away from centre  of circle
        Vector circleCentre = findCentreRight(start);
//        Might need a range agn instead of exactly matching
        if(calculateStraightDistance(end, circleCentre) != RADIUS) return null;

//        Determine angle
        Vector circleToStart = new Vector(start.getX()-circleCentre.getX(), start.getY()-circleCentre.getY());
        Vector circleToEnd = new Vector(start.getX()-circleCentre.getX(), end.getY()-circleCentre.getY());
        double angle = calculateAngle(circleToStart, circleToEnd);
        if(angle > 0) angle -= 2 * Math.PI;

        double arcLen = calculateArcLength(angle);
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move(Move.Dir.R, arcLen));
        return new Path(moves);

    }


    private Vector findCentreRight(Position p1){
        double circleTheta = p1.getTheta() - Math.PI/2;
        double circleY = p1.getY() + Math.sin(circleTheta) * RADIUS;
        double circleX = p1.getX() + Math.sin(circleTheta) * RADIUS;
//      Returning position seems erroneous since this is a point. Should we create a new class?
        return new Vector(circleX, circleY);
    }


    private Vector findCentreLeft(Position p1){
        double circleTheta = p1.getTheta() + Math.PI/2;
        double circleY = p1.getY() + Math.sin(circleTheta) * RADIUS;
        double circleX = p1.getX() + Math.sin(circleTheta) * RADIUS;
//      Returning position seems erroneous since this is a point. Should we create a new class?
        return new Vector(circleX, circleY);
    }

    private double calculateArcLength(double angle){
        return Math.abs(angle * RADIUS);
    }

    private double calculateAngle(Vector V1, Vector V2){
        return Math.atan2(V2.getY(), V2.getX()) - Math.atan2(V1.getY(), V1.getX());
    }

    private double calculateStraightDistance(Position start, Position end){
        // Calulate distance via hypot
        double delY = Math.abs(start.getY() - end.getY());
        double delX = Math.abs(start.getX() - end.getX());
        double distance = Math.hypot(delX, delY);
        return distance;
    }

    private double calculateStraightDistance(Vector start, Vector end){
        // Calulate distance via hypot
        double delY = Math.abs(start.getY() - end.getY());
        double delX = Math.abs(start.getX() - end.getX());
        double distance = Math.hypot(delX, delY);
        return distance;
    }


    //    For Vector
    private double calculateStraightDistance(Position start, Vector end){
        // Calulate distance via hypot
        double delY = Math.abs(start.getY() - end.getY());
        double delX = Math.abs(start.getX() - end.getX());
        double distance = Math.hypot(delX, delY);
        return distance;
    }

}
