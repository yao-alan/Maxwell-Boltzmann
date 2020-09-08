import java.util.*;
import java.util.Timer;

import javax.swing.*;

public class Runnable extends WindowMethods { //main game loop

    private static int fps = 60;
    private static Projectile projectileList[] = new Projectile[1000];
    private static int velocity = 2;
    private static int velocityDist[] = new int[100 * velocity];
    public static void main(String[] args) {

        //setting up main window
        JFrame window = new JFrame("Maxwell-Boltzmann");
        WindowMethods windowMethods = new WindowMethods();
        windowMethods.windowSetup(window);

        //spawning all projectiles
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) { 
                projectileList[10 * i + j] = new Projectile(0 + 15 * i, 400 + 20 * j, velocity);
                velocityDist[(int)(velocity * 10)]++;
            }
        }

        //adding Draw
        Draw draw = new Draw(projectileList, velocityDist);
        window.add(draw);

        //adding collision detection
        CollisionDetector collisionDetector = new CollisionDetector(projectileList);

        window.setVisible(true);

        //adding main game loop (runGame)
        Timer timer = new Timer();
        TimerTask runGame = new TimerTask() {
            public void run() {
                //find if collisions have occurred
                collisionDetector.resolveCollisions();

                //moves projectiles
                draw.update();
            }
        };
        TimerTask drawGraph = new TimerTask() {
            public void run() {

            }
        };

        double delay = 1000;
        double period = 1000 / fps;
        timer.scheduleAtFixedRate(runGame, (long) delay, (long) period);
    }

}