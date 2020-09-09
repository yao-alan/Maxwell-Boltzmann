import java.io.*;
import java.util.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

public class Display extends JPanel {

    private static ArrayList<Charge> chargeList = new ArrayList<Charge>();
    private static int width, height;
    private static boolean mousePressed = false;
    private static int mouseX, mouseY;

    public static void main(String[] args) {

        JFrame display = new JFrame("Electric Field Lines");
        setupWindow(display);
        width = display.getContentPane().getWidth();
        height = display.getContentPane().getHeight();
        display.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                ;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mousePressed = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mousePressed = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ;
            }
            
        });
        display.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                mouseX = e.getX() - 15;
                mouseY = e.getY() - 80;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX() - 15;
                mouseY = e.getY() - 80;
            }

        });

        JButton generateCharge = new JButton("Generate New Charge");
        display.add(generateCharge, BorderLayout.NORTH);

        generateCharge.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // menu setup
                JFrame menu = new JFrame("Charge Magnitude");
                JLabel label = new JLabel("Enter charge in Coulombs");
                JTextField chargeMagnitude = new JTextField();
                JButton enter = new JButton("Submit");
                menu.add(label, BorderLayout.NORTH);
                menu.add(chargeMagnitude, BorderLayout.CENTER);
                menu.add(enter, BorderLayout.SOUTH);
                setupWindow(menu);
                menu.setSize(300, 300);
                menu.setLocationRelativeTo(null);

                // adding new charges
                enter.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int charge;
                        if (chargeMagnitude.getText() == "")
                            charge = 1;
                        else
                            charge = Integer.parseInt(chargeMagnitude.getText());
                        chargeList.add(new Charge(charge));
                        menu.dispose();
                        display.requestFocus();
                    }
                });

            }
        });

        Field field = new Field(chargeList, display.getWidth(), display.getHeight());

        Draw draw = new Draw(chargeList, field);
        display.add(draw);

        Timer timer = new Timer();
        TimerTask run = new TimerTask() {
            public void run() {

                draw.updateScreen();
                width = display.getContentPane().getWidth();
                height = display.getContentPane().getHeight();
                System.out.println(mouseX + " " + mouseY);

                if (mousePressed == true) {
                    for (int i = 0; i < chargeList.size(); i++) {
                        Charge currentCharge = chargeList.get(i);
                        int currentX = currentCharge.getXCoord();
                        int currentY = currentCharge.getYCoord();
                        System.out.println(mouseX + " " + mouseY);
                        if (currentX - currentCharge.diameter <= mouseX
                            && mouseX <= currentX + currentCharge.diameter 
                            && currentY - currentCharge.diameter <= mouseY 
                            && mouseY <= currentY + currentCharge.diameter
                            ) {
                                currentCharge.updatePos(mouseX, mouseY);
                            }
                    }
                } 
            }
        };

        double delay = 0.0;
        double period = 1;
        timer.scheduleAtFixedRate(run, (long) delay, (long) period);

    }

    public static void setupWindow(JFrame display) {

        int screenWidth, screenHeight;
        Dimension screenSize;

        // getting screenSize
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) (screenSize.getWidth());
        screenHeight = (int) (screenSize.getHeight());

        // setting up display
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setSize(screenWidth, screenHeight);
        display.setLocationRelativeTo(null);
        display.setResizable(true);
        display.setVisible(true);
        display.getRootPane().setDoubleBuffered(true);

    }

}