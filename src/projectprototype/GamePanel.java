/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author juven1996
 */
public class GamePanel extends JPanel implements MouseListener {
    
    protected ArrayList<Circle> objectList = new ArrayList<>();
    protected final int CIRCLESIZE = 30;
    public GamePanel() {
        setBackground(Color.WHITE);
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border, "Game Panel will be below");
        setBorder(border);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Circle circle : objectList) {
            g.setColor(circle.color);
            g.fillOval(circle.origin.x, circle.origin.y, CIRCLESIZE, CIRCLESIZE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        objectList.add(new Circle(e.getX()-CIRCLESIZE/2, e.getY()-CIRCLESIZE/2, Color.red));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
