import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Draw extends JPanel {

    private ArrayList <Charge> chargeList = new ArrayList <Charge>();
    private Field field;

    public Draw(ArrayList <Charge> chargeList, Field field) {
        
        this.chargeList = chargeList;
        this.field = field;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        field.draw(g);
        for (int i = 0; i < chargeList.size(); i++) {
            chargeList.get(i).draw(g);
        }
    }

    public void updateScreen() {
        repaint();
    }

}