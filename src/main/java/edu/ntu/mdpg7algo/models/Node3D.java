package edu.ntu.mdpg7algo.models;

import lombok.Data;

import java.util.Random;

@Data
public class Node3D {

    public static final int DIR = 3;  // TODO: probably switch to 4 or 6

    // TODO: R = 6, 6.75 DEG, CUSTOM R & DEG NEEDED
    public static final double[] DX = {0, -0.0415893, 0.0415893};
    public static final double[] DY = {0.7068582, 0.705224, 0.7052241};
    public static final double[] DT = {0, 0.1178097, -0.1178097};

    public static final int HEADINGS = 72;
    public static final double DELTA_HEADING_DEG = 360 / (double) HEADINGS;
    public static final double DELTA_HEADING_RAD = 2 * Math.PI / (double) HEADINGS;
    public static final double NEGATIVE_DELTA_HEADING_RAD = 2 * Math.PI - DELTA_HEADING_RAD;
    public static final double TURN_PENALTY = 1.05;
    public static final double REVERSE_PENALTY = 2.0;
    public static final double COD_PENALTY = 2.0;

    public static final int DUBINS_SHOT_DISTANCE = 100;

    private int index, motion;
    private double x, y, t, g, h;
    private boolean open, close;
    private Node3D predecessor;

    public Node3D() {
        this(0, 0, 0, 0, 0, null, 0);
    }

    public Node3D(double x, double y, double t, double g, double h, Node3D predecessor, int motion) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.g = g;
        this.h = h;
        this.open = false;
        this.close = false;
        this.predecessor = predecessor;
        this.index = -1;
        this.motion = motion;
    }

    public double getGH() {
        return g + h;
    }

    public int setIndex(int width, int height) {
        int tHeading = (int) (t / DELTA_HEADING_RAD);
        index = tHeading * width * height + (int) y * width + (int) x;
        return index;
    }

    public void open() {
        open = true;
        close = false;
    }

    public void close() {
        open = false;
        close = true;
    }

    public boolean isOnGrid(int width, int height) {
        int tHeading = (int) (t / DELTA_HEADING_RAD);
        return (0 <= x && x < width) && (0 <= y && y < height) && (0 <= tHeading && tHeading < HEADINGS);
    }

    public boolean isInRange(Node3D goal) {
//        int random = new Random().nextInt(0, 11);
        int random = 5;
        double dx = Math.abs(x - goal.x) / random;
        double dy = Math.abs(y - goal.y) / random;
        return (dx * dx) + (dy * dy) < DUBINS_SHOT_DISTANCE;
    }

    public Node3D createSuccessor(int deltaIndex) {
        double xSuccessor, ySuccessor, tSuccessor;

        if (deltaIndex < 3) {
            xSuccessor = x + DX[deltaIndex] * Math.cos(t) - DY[deltaIndex] * Math.sin(t);
            ySuccessor = y + DX[deltaIndex] * Math.sin(t) + DY[deltaIndex] * Math.cos(t);
            tSuccessor = normalizeHeadingRad(t + DT[deltaIndex]);
        }
        else {
            xSuccessor = x - DX[deltaIndex - 3] * Math.cos(t) - DY[deltaIndex - 3] * Math.sin(t);
            ySuccessor = y - DX[deltaIndex - 3] * Math.sin(t) + DY[deltaIndex - 3] * Math.cos(t);
            tSuccessor = normalizeHeadingRad(t + DT[deltaIndex - 3]);
        }

        return new Node3D(xSuccessor, ySuccessor, tSuccessor, g, 0, this, index);
    }

    public void updateG() {
        if (motion < 3) {
            if (predecessor.motion != motion) {
                g += predecessor.motion > 2 ? DX[0] * TURN_PENALTY * COD_PENALTY : DX[0] * TURN_PENALTY;
            }
            else {
                g += DX[0];
            }
        }
        else {
            if (predecessor.motion != motion) {
                double dg = DX[0] * TURN_PENALTY * REVERSE_PENALTY;
                g += predecessor.motion < 3 ? dg * COD_PENALTY : dg;
            }
            else {
                g += DX[0] * REVERSE_PENALTY;
            }
        }
    }

    public boolean equals(Node3D node) {
        return ((int) x == (int) node.x && (int) y == (int) node.y &&
                (Math.abs(t - node.t) <= DELTA_HEADING_RAD || Math.abs(t - node.t) >= NEGATIVE_DELTA_HEADING_RAD));
    }

    public static double normalizeHeadingRad(double t) {
        if (t < 0) {
            t = t - 2. * Math.PI * (int)(t / (2. * Math.PI));
            return 2. * Math.PI + t;
        }
        return t - 2. * Math.PI * (int)(t / (2. * Math.PI));
    }
}
