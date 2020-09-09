import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Charge extends JPanel {

    private int screenWidth, screenHeight;
    private Dimension screenSize;
    private int x, y;
    public int diameter;
    private int charge;

    public Charge(int chargeMagnitude) {

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        x = screenWidth / 2;
        y = screenHeight / 2;
        charge = chargeMagnitude;
        diameter = Math.abs(20 * charge);

    }
        

    public void draw(Graphics g) {

        Graphics2D g2D = (Graphics2D) g;
        if (charge <= 0)
            g2D.setColor(Color.BLUE);
        else 
            g2D.setColor(Color.RED);
        g2D.draw(new Ellipse2D.Double(x - diameter / 2, y - diameter / 2, diameter, diameter));
        g2D.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);

    }

    public void updatePos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getXCoord() {

        return x;

    }

    public int getYCoord() {

        return y;

    }

    public int getCharge() {

        return charge;

    }

    public int getDiameter() {

        return diameter;

    }

}