package edu.ntu.mdpg7algo.visualization;

import edu.ntu.mdpg7algo.models.Arena;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

@Data
public class ArenaPanel extends JPanel {

    private static final int NUM_ROW = 20, NUM_COL = 20;
    private int x0, y0, w, h, cellW, cellH;
    private Arena arena;

    public ArenaPanel(int x0, int y0, int cellW, int cellH, Arena arena) {
        this.x0 = x0;
        this.y0 = y0;
        this.cellW = cellW;
        this.cellH = cellH;
        this.arena = arena;

        w = cellW * NUM_COL;
        h = cellH * NUM_ROW;
        setSize(new Dimension(w, h));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // check bounds
                int x = e.getX(), y = e.getY();
                if (x < x0 || y < y0 || x0 + w < x || y0 + h < y) {
                    System.out.println("Mouse click performed outside arena, ignored");
                    return;
                }
                int cellX = getCellX(x), cellY = getCellY(y);
                System.out.println("cell x: " + cellX + ", cell y: " + cellY);
            }
        });
    }

    private int getCellX(double absX) {
        if (absX < x0 || x0 + w < absX) {
            return -1;
        }
        return (int) ((absX - x0) / cellW);
    }

    private int getCellY(double absY) {
        if (absY < y0 || y0 + h < absY) {
            return -1;
        }
        return 19 - (int) ((absY - y0) / cellH);
    }

    private int convertGridY(int cellY) {
        return 19 - cellY;
    }


    public void paint(Graphics g) {
        // draw horizontal lines
        for (int i = 0; i <= NUM_ROW; i++) {
            g.drawLine(x0, y0 + i * cellH, x0 + w, y0 + i * cellH);
            if (i != 0) {
                g.drawString("" + (20 - i), x0, y0 + i * cellH);
            }
        }
        for (int i = 0; i <= NUM_COL; i++) {
            g.drawLine(x0 + i * cellW, y0, x0 + i * cellW, y0 + h);
            if (i != 20) {
                g.drawString("" + i, x0 + i * cellW, y0 + h);
            }
        }

        try {
            Image image = ImageIO.read(new File("assets/robot.jpg"));
            image = image.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            double carX = arena.getCar().getPosition().getX(), carY = arena.getCar().getPosition().getY();
            g.drawImage(image, (int) carX, (int) carY, this);
        }
        catch (IOException ex) {
            // handle exception...
        }

        for (int i = 0; i < NUM_COL; i++) {
            for (int j = 0; j < NUM_ROW; j++) {
                if (arena.getObstaclesMatrix()[i][j] != null) {
                    g.fillRect(x0 + j * cellW, y0 + convertGridY(i) * cellH, cellW, cellH);
                }
            }
        }
    }
}
