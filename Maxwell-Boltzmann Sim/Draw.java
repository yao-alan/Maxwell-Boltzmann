import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import java.text.*;
import java.util.*;

public class Draw extends JPanel {

    public Projectile projectileList[];
    private int velocity = 2;
    private double mpSpeed = 0;
    private double avgSpeed = 0;
    private double rmsSpeed = 0;
    private int velocityDist[] = new int[100 * velocity];
    private GeneralPath graph = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 2 * velocityDist.length);
    private int width = 10;
    private double height = 2;
    private boolean firstDraw = true;
    private BufferedImage graphImg;

    public Draw(Projectile projectileList[], int velocityDist[]) {
        this.projectileList = projectileList;
        this.velocityDist = velocityDist;
    }

    public void paintComponent(Graphics g) {
        int graphPeak[][] = new int[1][2];
        graphPeak[0][0] = 0;
        graphPeak[0][1] = 0;
        super.paintComponent(g);
        //draw velocity distribution, divide into 10 * (10 * velocity) sections
        for (int i = 0; i < 100 * velocity; i++) {
            g.setColor(Color.WHITE);
            g.drawRect(200 + width * i, (int)(280 - (height * velocityDist[i] * 1000 / projectileList.length)), width, (int)(height * velocityDist[i] * 1000 / projectileList.length));
            g.setColor(Color.BLACK);
            g.fillRect(200 + width * i, (int)(280 - height * velocityDist[i] * 1000 / projectileList.length), width, (int)(height * velocityDist[i] * 1000 / projectileList.length));
            if (velocityDist[i] > graphPeak[0][1]) {
                graphPeak[0][0] = i;
                graphPeak[0][1] = velocityDist[i];
            }
        }
        mpSpeed = graphPeak[0][0] * 0.1;
        //draw Maxwell-Boltzmann graph
        if (firstDraw) {
            double funcVal[] = new double[2 * velocityDist.length];
            double kT = 1.0 / 2.0; //in 2D, v_rms = sqrt(2kT/m); m = 1, v_rms = 1
            for (int i = 0; i < velocityDist.length * 2; i++) {
                funcVal[i] = ((0.05 * i / velocity) / kT) * Math.exp(-Math.pow(0.05 * i / velocity, 2)/(2 * kT));
            }
            firstDraw = false;
            graph.moveTo(200, 280);
            for (int i = 1; i < velocityDist.length * 2; i++) {
                graph.lineTo(200 + (width * i) / 2, 280 - (funcVal[i] * height * 25 * 2));
            }
            graphImg = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = graphImg.createGraphics();
            g2.setColor(Color.RED);
            g2.draw(graph);
            g2.dispose();
        }
        else {
            g.drawImage(graphImg, 0, 0, null);
        }
        //draw dividing line
        g.setColor(Color.BLACK);
        g.drawLine(0, 300, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 300);
        //draw projectiles
        for (int i = 0; i < projectileList.length; i++) { 
            if (i == projectileList.length / 8 || i == projectileList.length / 2 || i == projectileList.length)
                g.setColor(Color.RED);
            else if (i == projectileList.length / 4 || i == projectileList.length * 2 / 3 || i == projectileList.length - 1)
                g.setColor(Color.BLUE);
            else if (i == projectileList.length / 6 || i == projectileList.length / 3 || i == projectileList.length - 2)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.BLACK);
            projectileList[i].draw(g);
        }
        //draw current speeds
        g.setColor(Color.BLACK);
        DecimalFormat df = new DecimalFormat("#.##");
        g.drawString("Most-Probable: " + df.format(mpSpeed) + "_", 400, 100);
        g.drawString("Average: " + Double.toString(avgSpeed), 400, 125);
        g.drawString("Root-Mean-Square: " + Double.toString(rmsSpeed), 400, 150);
    }

    public void update() {
        Arrays.fill(velocityDist, 0);
        for (int i = 0; i < projectileList.length; i++) {
            projectileList[i].move();
            avgSpeed += projectileList[i].velocity;
            rmsSpeed += Math.pow(projectileList[i].velocity, 2);
            velocityDist[(int)(projectileList[i].velocity / 0.1)]++;
        }
        avgSpeed /= projectileList.length;
        rmsSpeed = Math.sqrt(rmsSpeed / projectileList.length);
        repaint();
    }

}