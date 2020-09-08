import java.awt.*;

public class CollisionDetector {

    public Projectile projectileList[];

    public CollisionDetector(Projectile projectileList[]) {
        this.projectileList = projectileList;
    }

    public void resolveCollisions() {
        for (int i = 0; i < projectileList.length; i++) {
            double x = projectileList[i].x;
            double y = projectileList[i].y;
            double lastX = projectileList[i].lastX;
            double lastY = projectileList[i].lastY;
            double theta = projectileList[i].theta;
            //collision with top wall
            if (projectileList[i].y <= 300) {
                if ((lastX - x) != 0) {
                    double m = (lastY - y)/(lastX - x);
                    double b = y - (m * x);
                    projectileList[i].x = (300 - b) / m;
                }
                projectileList[i].y = 300;
                projectileList[i].theta = (2 * Math.PI - theta);
                x = projectileList[i].x;
                y = projectileList[i].y;
                lastX = projectileList[i].lastX;
                lastY = projectileList[i].lastY;
                theta = projectileList[i].theta;
            }
            //collision with bottom wall
            if (y >= Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50) {
                if ((lastX - x) != 0) {
                    double m = (lastY - y)/(lastX - x);
                    double b = y - (m * x);
                    projectileList[i].x = (Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50 - b) / m;
                }
                projectileList[i].y = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 50;
                projectileList[i].theta = (2 * Math.PI - theta);
                x = projectileList[i].x;
                y = projectileList[i].y;
                lastX = projectileList[i].lastX;
                lastY = projectileList[i].lastY;
                theta = projectileList[i].theta;
            }
            //collision with left wall
            if (x <= 0) {
                if ((lastX - x) != 0) {
                    double m = (lastY - y)/(lastX - x);
                    double b = y - (m * x);
                    projectileList[i].y = m * x + b;
                }
                projectileList[i].x = 0;
                if (theta <= Math.PI)
                    projectileList[i].theta = Math.PI - theta;
                else    
                    projectileList[i].theta = Math.PI + (2 * Math.PI - theta);
                x = projectileList[i].x;
                y = projectileList[i].y;
                lastX = projectileList[i].lastX;
                lastY = projectileList[i].lastY;
                theta = projectileList[i].theta;
            }
            //collision with right wall
            if (x >= Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 20) {
                if ((lastX - x) != 0) {
                    double m = (lastY - y)/(lastX - x);
                    double b = y - (m * x);
                    projectileList[i].y = m * x + b;
                }
                projectileList[i].x = Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 20;
                if (theta <= Math.PI)
                    projectileList[i].theta = Math.PI - theta;
                else    
                    projectileList[i].theta = Math.PI + (2 * Math.PI - theta);
                x = projectileList[i].x;
                y = projectileList[i].y;
                lastX = projectileList[i].lastX;
                lastY = projectileList[i].lastY;
                theta = projectileList[i].theta;
            }
            //inefficient collision with other balls (O(n^2) pairwise)
            for (int j = 0; j < projectileList.length; j++) {
                if (j == i)
                    ;
                else {
                    //two balls collide if distance between centers of balls is 2 * radius
                    if (Math.pow(projectileList[i].centerX - projectileList[j].centerX, 2) 
                    + Math.pow(projectileList[i].centerY - projectileList[j].centerY, 2) <= Math.pow(projectileList[i].diameter, 2)) {
                        //v1_f = v1 - [[2 * m2 / (m1 + m2)] * [(v1 - v2) dot (p1 - p2) / (||p1 - p2|| ^ 2)] * (p1 - p2)]
                        //p = vector representing center of ball
                        double v1_x = projectileList[i].velocity * Math.cos(projectileList[i].theta);
                        double v1_y = projectileList[i].velocity * Math.sin(projectileList[i].theta); 
                        double v2_x = projectileList[j].velocity * Math.cos(projectileList[j].theta);
                        double v2_y = projectileList[j].velocity * Math.sin(projectileList[j].theta);
                        double p1_x = projectileList[i].centerX;
                        double p1_y = projectileList[i].centerY;
                        double p2_x = projectileList[j].centerX;
                        double p2_y = projectileList[j].centerY;
                        //k = [[2 * m2 / (m1 + m2)] * [(v1 - v2) dot (p1 - p2) / (||p1 - p2|| ^ 2)]
                        //ideal gas: m1 = m2
                        double k1 = ((v1_x - v2_x) * (p1_x - p2_x) + (v1_y - v2_y) * (p1_y - p2_y)) / (Math.pow(p1_x - p2_x, 2) + Math.pow(p1_y - p2_y, 2));
                        double v1f_x = v1_x - k1 * (p1_x - p2_x);
                        double v1f_y = v1_y - k1 * (p1_y - p2_y);
                        projectileList[i].velocity = Math.sqrt(Math.pow(v1f_x, 2) + Math.pow(v1f_y, 2));
                        if (v1f_x >= 0 && v1f_y >= 0)
                            projectileList[i].theta = Math.atan(v1f_y / v1f_x);
                        else if (v1f_x <= 0 && v1f_y >= 0)
                            projectileList[i].theta = Math.PI - Math.atan(v1f_y / Math.abs(v1f_x));
                        else if (v1f_x >= 0 && v1f_y <= 0)
                            projectileList[i].theta = Math.atan(v1f_y / v1f_x);
                        else if (v1f_x <=0 && v1f_y <= 0)
                            projectileList[i].theta = Math.PI + Math.atan(v1f_y / v1f_x);
                        //k = [[2 * m2 / (m1 + m2)] * [(v2 - v1) dot (p2 - p1) / (||p2 - p1|| ^ 2)]
                        double k2 = ((v2_x - v1_x) * (p2_x - p1_x) + (v2_y - v1_y) * (p2_y - p1_y)) / (Math.pow(p2_x - p1_x, 2) + Math.pow(p2_y - p1_y, 2));
                        double v2f_x = v2_x - k2 * (p2_x - p1_x);
                        double v2f_y = v2_y - k2 * (p2_y - p1_y);
                        projectileList[j].velocity = Math.sqrt(Math.pow(v2f_x, 2) + Math.pow(v2f_y, 2));
                        if (v2f_x >= 0 && v2f_y >= 0)
                            projectileList[j].theta = Math.atan(v2f_y / v2f_x);
                        else if (v2f_x <= 0 && v2f_y >= 0)
                            projectileList[j].theta = Math.PI - Math.atan(v2f_y / Math.abs(v2f_x));
                        else if (v2f_x >= 0 && v2f_y <= 0)
                            projectileList[j].theta = Math.atan(v2f_y / v2f_x);
                        else if (v2f_x <=0 && v2f_y <= 0)
                            projectileList[j].theta = Math.PI + Math.atan(v2f_y / v2f_x);
                        projectileList[i].move();
                        projectileList[j].move();
                    }
                }
            }
        }
    }

}