package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Move;
import edu.ntu.mdpg7algo.models.Path;
import edu.ntu.mdpg7algo.models.Position;
import edu.ntu.mdpg7algo.models.Vector;

import java.util.ArrayList;

/*
Possible way to speed up pathfinding?
To determine what move to do, it is dependent on 2(?) factors (not taking into account constraints such as walls, hitting obstacles etc):
(Robot Start Dir, Robot End Dir), Possible Moves
(UP, RIGHT) - [L, LS, R, SR, RS, LR, RLR]





*/

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
                return computeStraightPath(start, end);
            case "L":
                return computeLeftPath(start, end);
            case "R":
                return computeRightPath(start, end);
        }


        return new Path();
    }


//    Calculation of paths

//    Single segment
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
        double angle = Vector.calculateAngle(circleToStart, circleToEnd, false);


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
        double angle = Vector.calculateAngle(circleToStart, circleToEnd, true);


        double arcLen = calculateArcLength(angle);
        ArrayList<Move> moves = new ArrayList<Move>();
        moves.add(new Move(Move.Dir.R, arcLen));
        return new Path(moves);
    }

/*
Double segment - Has to determine ONE intermediate position of the robot
*/
    private Path computeSRPath(Position start, Position end){

    // Check if possible - TO DO

        /*
        Find intermediate position by:
        1) Finding centre of circle of start and end position (C1, C2)
        2) Vector from C1 to C2 = V1
        3) Intermediate point = Start position + V1
        */
        Vector c1 = findCentreRight(start);
        Vector c2 = findCentreRight(end);
        Vector v1 = new Vector(c1, c2);

        Position intermediatePos = new Position(start.getX() + v1.getX(), start.getY() + v1.getY(), start.getTheta());
        /*
        Compute moves
        Straight distance + Arclen of Right move
        */
        Path path1 = computeStraightPath(start, intermediatePos);
        Path path2 = computeRightPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }


    private Path computeRSPath(Position start, Position end){

        // Check if possible - TO DO

        /*
        Find intermediate position by:
        1) Finding centre of circle of start and end position (C1, C2)
        2) Vector from C1 to C2 = V1
        3) Vector V2 = counterclockwise rotation of V1 by PI/2 -> V2 = (-V1y, V1x)
        3) Intermediate point = c1 + r/|V1| * V2
        */
        Vector c1 = findCentreRight(start);
        Vector c2 = findCentreRight(end);
        Vector v1 = new Vector(c1, c2);
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, Math.PI/2);

        double factor = RADIUS/v2.getLength();

        Position intermediatePos = new Position(start.getX() + factor * v2.getX(), start.getY() + factor * v2.getY(), end.getTheta());
        /*
        Compute moves
        Straight distance + Arclen of Right move
        */
        Path path1 = computeRightPath(start, intermediatePos);
        Path path2 = computeStraightPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }

    private Path computeSLPath(Position start, Position end){

        // Check if possible - TO DO

        /*
        Find intermediate position by:
        1) Finding centre of circle of start and end position (C1, C2)
        2) Vector from C1 to C2 = V1
        3) Vector V2 = counterclockwise rotation of V1 by PI/2 -> V2 = (-V1y, V1x)
        3) Intermediate point = c1 + r/|V1| * V2
        */
        Vector c1 = findCentreLeft(start);
        Vector c2 = findCentreLeft(end);
        Vector v1 = new Vector(c1, c2);
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, Math.PI/2);

        double factor = RADIUS/v2.getLength();

        Position intermediatePos = new Position(start.getX() + v1.getX(), start.getY() + v1.getY(), start.getTheta());
        /*
        Compute moves
        Straight distance + Arclen of Right move
        */

        Path path1 = computeStraightPath(start, intermediatePos);
        Path path2 = computeLeftPath(intermediatePos, end);

        Path[] paths = {path1, path2};

        return new Path(paths);
    }

    private Path computeLSPath(Position start, Position end){

        // Check if possible - TO DO

        /*
        Find intermediate position by:
        1) Finding centre of circle of start and end position (C1, C2)
        2) Vector from C1 to C2 = V1
        3) Vector V2 = counterclockwise rotation of V1 by PI/2 -> V2 = (-V1y, V1x)
        3) Intermediate point = c1 + r/|V1| * V2
        */
        Vector c1 = findCentreLeft(start);
        Vector c2 = findCentreLeft(end);
        Vector v1 = new Vector(c1, c2);
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, Math.PI*3/2);

        double factor = RADIUS/v2.getLength();

        Position intermediatePos = new Position(start.getX() + factor * v2.getX(), start.getY() + factor * v2.getY(), end.getTheta());
        /*
        Compute moves
        Straight distance + Arclen of Right move
        */
        Path path1 = computeLeftPath(start, intermediatePos);
        Path path2 = computeStraightPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }

    private Path computeRLPath(Position start, Position end){

        // Check if possible - TO DO

        /*
        Find intermediate position by:
        1) Finding centre of circle of start and end position (C1, C2)
        2) Vector from C1 to C2 = V1
        3) Intermediate position = 1/2 V1 + C1
        */
        Vector c1 = findCentreRight(start);
        Vector c2 = findCentreLeft(end);
        Vector v1 = new Vector(c1, c2);

        double factor = 0.5;

        double angle = Vector.calculateAngle(c1, v1, true);

        Position intermediatePos = new Position(start.getX() + factor * v1.getX(), start.getY() + factor * v1.getY(), angle);
        /*
        Compute moves
        Straight distance + Arclen of Right move
        */
        Path path1 = computeRightPath(start, intermediatePos);
        Path path2 = computeLeftPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }

    private Path computeLRPath(Position start, Position end){

        // Check if possible - TO DO

        /*
        Find intermediate position by:
        1) Finding centre of circle of start and end position (C1, C2)
        2) Vector from C1 to C2 = V1
        3) Intermediate position = 1/2 V1 + C1
        */
        Vector c1 = findCentreLeft(start);
        Vector c2 = findCentreRight(end);
        Vector v1 = new Vector(c1, c2);

        double factor = 0.5;

        double angle = Vector.calculateAngle(c1, v1, false);

        Position intermediatePos = new Position(start.getX() + factor * v1.getX(), start.getY() + factor * v1.getY(), angle);
        /*
        Compute moves
        Straight distance + Arclen of Right move
        */
        Path path1 = computeLeftPath(start, intermediatePos);
        Path path2 = computeRightPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }




/*
Triple segment - Has to determine TWO intermediate position
*/



//    Finding vectors

    private Vector findCentreRight(Position p1){
        double circleTheta = p1.getTheta() - Math.PI/2;
        double circleY = p1.getY() + Math.sin(circleTheta) * RADIUS;
        double circleX = p1.getX() + Math.cos(circleTheta) * RADIUS;
//      Returning position seems erroneous since this is a point. Should we create a new class?
        return new Vector(circleX, circleY);
    }


    private Vector findCentreLeft(Position p1){
        double circleTheta = p1.getTheta() + Math.PI/2;
        double circleY = p1.getY() + Math.sin(circleTheta) * RADIUS;
        double circleX = p1.getX() + Math.cos(circleTheta) * RADIUS;
//      Returning position seems erroneous since this is a point. Should we create a new class?
        return new Vector(circleX, circleY);
    }


//    Basic calculations of distance and angle
    private double calculateArcLength(double angle){
        return Math.abs(angle * RADIUS);
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
