import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Field extends JPanel {

    private ArrayList <Charge> chargeList = new ArrayList <Charge>();
    private int width, height;
    private double fieldX, fieldY, fieldMagnitude;
    private double radius;

    public Field(ArrayList <Charge> chargeList, int screenWidth, int screenHeight) {

        this.chargeList = chargeList;
        width = screenWidth;
        height = screenHeight;

    }

    public void draw(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);
        for (int x = 0; x <= width; x += 40) {
            for (int y = 0; y <= height; y += 40) {
                fieldVector(x, y);
                if (fieldMagnitude > 10)
                    g2D.drawLine(x, y, (int) (20 * fieldX / fieldMagnitude) + x, (int) (20 * fieldY / fieldMagnitude) + y);
                else
                    g2D.drawLine(x, y, (int) fieldX + x, (int) fieldY + y);
            }
        }

    }

    public void fieldVector(int x, int y) {

        fieldX = 0.0;
        fieldY = 0.0;
        double k, r2;
        for (int i = 0; i < chargeList.size(); i++) {

            int theta;
            int dx, dy;
            int q = chargeList.get(i).getCharge();
            k = 1 / (4 * Math.PI * 8.854 * Math.pow(10, -12));
            dx = x - chargeList.get(i).getXCoord();
            dy = y - chargeList.get(i).getYCoord();
            r2 = Math.pow(dx, 2) + Math.pow(dy, 2);
            fieldX += 0.25 * k * (q / r2) * (dx / r2);
            fieldY += 0.25 * k * (q / r2) * (dy / r2);
            fieldMagnitude = Math.sqrt(Math.pow(fieldX, 2) + Math.pow(fieldY, 2));

        }

    }

}