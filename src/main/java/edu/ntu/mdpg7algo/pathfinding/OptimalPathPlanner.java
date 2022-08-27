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
       return switch (pathType){
            case "S" ->
                computeStraightPath(start, end);

            case "L" ->
                computeLeftPath(start, end);

            case "R"->
                computeRightPath(start, end);

            case "SR"->
                computeSRPath(start, end);

            case "SL"->
                 computeSLPath(start, end);

            case "RL"->
                 computeRLPath(start, end);

            case "LR"->
                 computeLRPath(start, end);

            case "LS"->
                 computeLSPath(start, end);

            case "RS"->
                 computeRSPath(start, end);

            case "RSR"->
                 computeRSRPath(start, end);

            case "LSL"->
                 computeLSLPath(start, end);

            case "LSR"->
                 computeLSRPath(start, end);

            case "RSL"->
                 computeRSLPath(start, end);

            case "LRL"->
                    computeLRLPath(start, end);

            case "RLR"->
                 computeRLRPath(start, end);
        };

    }


//    Calculation of paths

//    Single segment - "S", "R", "L",
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
"SR", "SL", "RL", "LR", "LS", "RS",
*/
    private Path computeSRPath(Position start, Position end){

    // Check if possible - TODO

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

        Position intermediatePos = new Position(c1.getX() + factor * v2.getX(), c1.getY() + factor * v2.getY(), end.getTheta());
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

        Position intermediatePos = new Position(c1.getX() + factor * v2.getX(), c1.getY() + factor * v2.getY(), end.getTheta());
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
        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));

//

        double factor = 0.5;

        double angle = Vector.calculateAngle(c1ToStart, v1, true);
        double newTheta = start.getTheta() - angle;

        Position intermediatePos = new Position(c1.getX() + factor * v1.getX(), c1.getY() + factor * v1.getY(), newTheta);
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
        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));
        double factor = 0.5;

        double angle = Vector.calculateAngle(c1ToStart, v1, false);
        double newTheta = start.getTheta() + angle;

        Position intermediatePos = new Position(c1.getX() + factor * v1.getX(), c1.getY() + factor * v1.getY(), newTheta);
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
"RSR", "LSL", "LSR", "RSL", "LRL", "RLR"
Start -> Inter_1 -> Inter_2 -> End
*/
    private Path computeRSRPath(Position start, Position end){
//        Check if possible - TODO

//        If possible
//        Centre of circles
        Vector c1  = findCentreRight(start);
        Vector c2 = findCentreRight(end);

//        Vectors to aid in finding intermediate
        Vector v1 = new Vector(c1, c2);
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, Math.PI/2);
        double factor = RADIUS / v2.getLength();

        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));

        double interX = c1.getX() + v2.getX()*factor;
        double interY = c1.getY() + v2.getY()*factor;
//        Angle of turn
        double angle = Vector.calculateAngle(c1ToStart, v2, true);
        double newTheta = start.getTheta() - angle;

        Position intermediatePos = new Position(interX, interY, newTheta);

        Path path1 = computeRightPath(start, intermediatePos);
        Path path2 = computeSRPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }


    private Path computeRSLPath(Position start, Position end){
        //        Check if possible - TODO
        //        If possible
//        Centre of circles
        Vector c1  = findCentreRight(start);
        Vector c2 = findCentreLeft(end);

//        Vectors to aid in finding intermediate
        Vector v1 = new Vector(c1, c2);

        double angleOfRotation = Math.acos(2 * RADIUS/v1.getLength());
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, angleOfRotation);
        double factor = RADIUS * v2.getLength();

        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));
//        Angle of turn
        double angle = Vector.calculateAngle(c1ToStart, v2, true);
        double newTheta = start.getTheta() - angle;

        double interX = c1.getX() + v2.getX()*factor;
        double interY = c1.getY() + v2.getY()*factor;

        Position intermediatePos = new Position(interX, interY, newTheta);

        Path path1 = computeRightPath(start, intermediatePos);
        Path path2 = computeSLPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);

    }

    private Path computeLSRPath(Position start, Position end){
        //        Check if possible - TODO
        //        If possible
//        Centre of circles
        Vector c1  = findCentreLeft(start);
        Vector c2 = findCentreRight(end);

//        Vectors to aid in finding intermediate
        Vector v1 = new Vector(c1, c2);

//        To account for counter-clockwise rotation, take -angle
        double angleOfRotation = Math.acos(2 * RADIUS/v1.getLength());
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, -angleOfRotation);
        double factor = RADIUS * v2.getLength();

        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));
//        Angle of turn
        double angle = Vector.calculateAngle(c1ToStart, v2, false);
        double newTheta = start.getTheta() + angle;

        double interX = c1.getX() + v2.getX()*factor;
        double interY = c1.getY() + v2.getY()*factor;

        Position intermediatePos = new Position(interX, interY, newTheta);

        Path path1 = computeLeftPath(start, intermediatePos);
        Path path2 = computeSRPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);

    }

    private Path computeLSLPath(Position start, Position end){
        //        Check if possible - TODO
        //        If possible
//        Centre of circles
        Vector c1  = findCentreLeft(start);
        Vector c2 = findCentreLeft(end);

//        Vectors to aid in finding intermediate
        Vector v1 = new Vector(c1, c2);
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, -Math.PI/2);
        double factor = RADIUS / v2.getLength();

        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));

        double interX = c1.getX() + v2.getX()*factor;
        double interY = c1.getY() + v2.getY()*factor;
//        Angle of turn
        double angle = Vector.calculateAngle(c1ToStart, v2, false);
        double newTheta = start.getTheta() + angle;

        Position intermediatePos = new Position(interX, interY, newTheta);

        Path path1 = computeLeftPath(start, intermediatePos);
        Path path2 = computeSLPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);

    }

    private Path computeRLRPath(Position start, Position end){
        //        Check if possible - TODO

//        Centre of circles
        Vector c1  = findCentreRight(start);
        Vector c2 = findCentreRight(end);

//        Vectors to aid in finding intermediate
        Vector v1 = new Vector(c1, c2);
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, -Math.PI/2);

        Vector middle = Vector.getMiddlePointVector(c1, c2);

        double distanceMiddleToP3 = Math.sqrt( Math.pow((2*RADIUS), 2) - Math.pow((v1.getLength()/2), 2));
        double factor = distanceMiddleToP3 / v2.getLength();

        double p3X = middle.getX() + factor * v2.getX();
        double p3Y = middle.getY() + factor * v2.getY();

        Vector p3 = new Vector(p3X, p3Y);

        Vector intermediateVector = Vector.getMiddlePointVector(c1, p3);


//        Angle of turn
        Vector c1Top3 = new Vector(c1, p3);
        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));
        double angle = Vector.calculateAngle(c1Top3, c1ToStart, false);
        double newTheta = start.getTheta() - angle;

        Position intermediatePos = new Position(intermediateVector.getX(), intermediateVector.getY(), newTheta);

        Path path1 = computeRightPath(start, intermediatePos);
        Path path2 = computeLRPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }

    private Path computeLRLPath(Position start, Position end){
        //        Check if possible - TODO
        //        Centre of circles
        Vector c1  = findCentreLeft(start);
        Vector c2 = findCentreLeft(end);

//        Vectors to aid in finding intermediate
        Vector v1 = new Vector(c1, c2);
        Vector v2 = Vector.vectorRotateCounterClockwise(v1, Math.PI/2);

        Vector middle = Vector.getMiddlePointVector(c1, c2);

        double distanceMiddleToP3 = Math.sqrt( Math.pow((2*RADIUS), 2) - Math.pow((v1.getLength()/2), 2));
        double factor = distanceMiddleToP3 / v2.getLength();

        double p3X = middle.getX() + factor * v2.getX();
        double p3Y = middle.getY() + factor * v2.getY();

        Vector p3 = new Vector(p3X, p3Y);

        Vector intermediateVector = Vector.getMiddlePointVector(c1, p3);


//        Angle of turn
        Vector c1Top3 = new Vector(c1, p3);
        Vector c1ToStart = new Vector(c1, new Vector(start.getX(), start.getY()));
        double angle = Vector.calculateAngle(c1Top3, c1ToStart, false);
        double newTheta = start.getTheta() + angle;

        Position intermediatePos = new Position(intermediateVector.getX(), intermediateVector.getY(), newTheta);

        Path path1 = computeLeftPath(start, intermediatePos);
        Path path2 = computeRLPath(intermediatePos, end);

        Path[] paths = {path1, path2};
        return new Path(paths);
    }



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
