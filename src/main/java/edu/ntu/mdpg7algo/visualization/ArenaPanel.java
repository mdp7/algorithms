package edu.ntu.mdpg7algo.visualization;

import edu.ntu.mdpg7algo.models.Arena;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Data
public class ArenaPanel extends JPanel {

    private static final int NUM_ROW = 20, NUM_COL = 20;
    int x0, y0, cellW, cellH, numRow, numCol;
    private Arena arena;

    public ArenaPanel(int x0, int y0, int cellW, int cellH, int numRow, int numCol, Arena arena) {
        setSize(cellW * numCol, cellH * numRow);
        this.x0 = x0;
        this.y0 = y0;
        this.cellW = cellW;
        this.cellH = cellH;
        this.numRow = numRow;
        this.numCol = numCol;
        this.arena = arena;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // handle here
            }
        });
    }

    public void paint(Graphics g) {
        for (int i = 0; i <= numRow; i++)
            g.drawLine(x0, y0 + i * cellH, x0 + numRow * cellW, y0 + i * cellH);

        for (int i = 0; i <= numCol; i++)
            g.drawLine(x0 + i * cellW, y0, x0 + i * cellW, y0 + numRow * cellH);

        try {
            BufferedImage image = ImageIO.read(new File("assets/robot.jpg"));
            g.drawImage(image, x0, y0, this); // see javadoc for more info on the parameters
        }
        catch (IOException ex) {
            // handle exception...
        }

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                if (arena.getObstaclesMatrix()[i][j] != null) {
                    g.fillRect(x0 + j * cellW, y0 + i * cellH, cellW, cellH);
                }
            }
        }
    }
}
