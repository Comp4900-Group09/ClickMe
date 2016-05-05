package projectprototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GamePanel extends JPanel implements MouseListener {
    
    /*List of objects on screen.*/
    protected ArrayList<Circle> objectList = new ArrayList<>();
    
    /*Default size of a circle. Default is 30.*/
    protected final int CIRCLESIZE = 30;
    
    /*GamePanel constructor.*/
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
            g.fillOval(circle.origin.x-CIRCLESIZE/2, circle.origin.y-CIRCLESIZE/2, CIRCLESIZE, CIRCLESIZE);
        }
        
        g.setColor(Color.BLACK);
        g.drawLine(Game.Width/2, 200, Game.Width/2, Game.Height-200);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        boolean inside = false;
        for(Circle circle : objectList) {
            if(circle.contains(x, y)) {
                inside = true;
            }
        }
        if(!inside)
            objectList.add(new Circle(e.getX(), e.getY(), CIRCLESIZE, Color.red, this));
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
    
    public void clear() {
        removeAll();
        repaint();
    }
}
