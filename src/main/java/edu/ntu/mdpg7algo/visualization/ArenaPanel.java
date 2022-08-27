package edu.ntu.mdpg7algo.visualization;

import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class ArenaPanel extends JPanel {
    int width, height;
    int rows, cols;

    public ArenaPanel(int w, int h, int r, int c) {
        setSize(width = w, height = h);
        width = w;
        height = h;
        rows = r;
        cols = c;
    }

    public void paint(Graphics g) {
        int rowHt = height / (rows);
        for (int i = 0; i < rows; i++)
            g.drawLine(0, i * rowHt, width, i * rowHt);

        int rowWid = width / (cols);
        for (int i = 0; i < cols; i++)
            g.drawLine(i * rowWid, 0, i * rowWid, height);
    }
}
