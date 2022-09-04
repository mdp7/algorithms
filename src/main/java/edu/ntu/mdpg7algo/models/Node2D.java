package edu.ntu.mdpg7algo.models;

import lombok.Data;

@Data
public class Node2D {

    public static int dir = 8;
    public static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    public static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    public int index;
    public int x, y;
    public double g, h;
    public boolean open, close, visited;
    public Node2D predecessor;

    public Node2D() {
        this(0, 0, 0f, 0f, null);
    }

    public Node2D(int x, int y, double g, double h, Node2D predecessor) {
        this.x = x;
        this.y = y;
        this.g = g;
        this.h = h;
        this.predecessor = predecessor;
    }

    public int setIndex(int width) {
        index = y * width + x;
        return index;
    }

    public double getGH() {
        return g + h;
    }

    public void open() {
        open = true;
        close = false;
    }

    public void close() {
        open = false;
        close = true;
    }

    public void reset() {
        open = false;
        close = false;
    }

    public void visit() {
        visited = true;
    }

    public void updateG() {
        g += movementCost(predecessor);
        visited = true;
    }

    public void updateH(Node2D goal) {
        h = movementCost(goal);
    }

    public double movementCost(Node2D node) {
        return Math.sqrt((x - node.x) * (x - node.x) + (y - node.y) * (y - node.y));
    }

    public boolean equals(Node2D node) {
        return x == node.x && y == node.y;
    }

    public boolean isOnGrid(int width, int height) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }

    /**
     * deltaIndex is the index of dx and dy
     */
    public Node2D createSuccessor(int deltaIndex) {
        return new Node2D(x + dx[deltaIndex], y + dy[deltaIndex], g, 0, this);
    }
}
