package projectprototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
    
    /*Temporary players*/
    protected Player player1 = new Player("Bob");
    protected Player player2 = new Player("AI");
    
    /*Rectangles signifying the players area (half the screen)*/
    protected Rectangle rect1, rect2;
    
    /*GamePanel constructor.*/
    public GamePanel(int width, int height) {
        setBackground(Color.WHITE);
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border);
        setBorder(border);
        setupArea(width, height);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Circle circle : objectList) {
            g.setColor(circle.color);
            g.fillOval(circle.origin.x-CIRCLESIZE/2, circle.origin.y-CIRCLESIZE/2, CIRCLESIZE, CIRCLESIZE);
        }
        
        g.drawString(player1.name + ": " + player1.hp, 5, 20);
        g.drawString(player2.name + ": " + player2.hp, Game.Width-50, 20);
        
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
                objectList.remove(circle);
                inside = true;
                break;
            }
        }
        if(!inside && objectList.size() < 3) {
            if(rect1.contains(x, y))
                objectList.add(new Circle(e.getX(), e.getY(), CIRCLESIZE, Color.red, this));
        }
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
    
    public void setupArea(int width, int height) {
        rect1 = new Rectangle(0, 0, width/2, height);
        rect2 = new Rectangle(width/2, 0, width/2, height);
    }
}
