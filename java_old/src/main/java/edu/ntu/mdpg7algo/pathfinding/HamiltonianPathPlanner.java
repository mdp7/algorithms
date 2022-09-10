package edu.ntu.mdpg7algo.pathfinding;

import edu.ntu.mdpg7algo.models.Arena;
import edu.ntu.mdpg7algo.models.Obstacle;
import lombok.Data;

import java.util.ArrayList;

@Data
public class HamiltonianPathPlanner {

    private Arena arena;
    private double minCost = 10000;
    private int[] order;

    private int iterations = 0;



    public HamiltonianPathPlanner(Arena arena) {
        this.arena = arena;
    }


    private void travellingSalesMan(double[][] dist, boolean[] visited, int cur, int numGoals, int count, double cost, int[] order){

//        If all goals reached
        if(count == numGoals){
//            if cost of path < current min
            if(cost < minCost) {
                minCost = cost;
                this.order = order;
            }
        }

//        Go to neighbor node
        for(int i = 0; i < numGoals; i++){
            this.iterations++;
            if(!visited[i]){
//                Cost to travel to next node
                double newCost = cost + dist[cur][i];
                if(newCost > minCost) continue;

                visited[i] = true;
//                Set order
                int[] newOrder = order.clone();
                newOrder[count] = i;


                travellingSalesMan(dist, visited, i, numGoals, count + 1, newCost, newOrder);
                visited[i] = false;
            }
        }

    }


    /*
    dist is a 2D array for distance between every obstacles/start
    numGoals = # of obstacles and Start
     */

    public ArrayList<Obstacle> planPath(){
        ArrayList<Obstacle> obstacles = arena.getObstacles();
        int numGoals = obstacles.size();

        /*
        set start point
        Create distance 2D array
         */
        double [][] dist = new double[obstacles.size()][obstacles.size()];

        for(int i = 0; i < obstacles.size(); i++){
            for (int j = 0; j < obstacles.size(); j++){
                if(i == j) dist[i][j] = Double.MAX_VALUE;

                else {
                    dist[i][j] = computeDistance(obstacles.get(i), obstacles.get(j));
                }
            }
        }

        boolean[] visited = new boolean[numGoals];
        int[] order = new int[numGoals];


        for(int i = 0; i < visited.length; i++){
            visited[i] = false;
            order[i] = 0;
        }
//      mark start as true
        visited[0] = true;
        travellingSalesMan(dist, visited, 0, numGoals, 1, 0, order);
        return orderObstacles();
    }

    public double computeDistance(Obstacle o1, Obstacle o2){
        double delX = o1.getX() - o2.getX();
        double delY = o1.getY() - o2.getY();
        return Math.hypot(delX, delY);
    }

    private ArrayList<Obstacle> orderObstacles(){
        ArrayList<Obstacle> orderedObstacle = new ArrayList<>();
        for(int i = 0; i < order.length; i++){
            orderedObstacle.add(arena.getObstacles().get(order[i]));
        }

        return orderedObstacle;

    }

}
