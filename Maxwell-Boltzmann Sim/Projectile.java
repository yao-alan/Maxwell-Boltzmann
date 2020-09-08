import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

public class Projectile extends JPanel {

    public double x, y;
    public double lastX, lastY;
    public double centerX, centerY;
    public double velocity;
    public double theta;
    public int diameter;

    public Projectile(double x, double y, double velocity) {
        diameter = 10;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        centerX = x + diameter / 2;
        centerY = y + diameter / 2;
        int i = (int) (Math.random() * 360) + 30; 
        theta = i * (2 * Math.PI) / 360;
    }

    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.draw(new Ellipse2D.Double(x, y, diameter, diameter));
        g2D.fillOval((int)x, (int)y, diameter, diameter);
    }

    public void move() {
        lastX = x;
        lastY = y;
        x += velocity * Math.cos(theta);
        y += velocity * Math.sin(theta);
        centerX = x + diameter / 2;
        centerY = y + diameter / 2;
    }

}