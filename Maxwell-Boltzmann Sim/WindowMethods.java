import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class WindowMethods extends JComponent {

    public int screenWidth;
    public int screenHeight;
    public Dimension screenSize;
    
    public void getScreenDimensions() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
    }

    public void windowSetup(JFrame window) {
        getScreenDimensions();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(screenWidth, screenHeight);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.getRootPane().setDoubleBuffered(true);
    }

}