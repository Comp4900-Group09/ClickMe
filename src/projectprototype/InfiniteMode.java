package projectprototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class InfiniteMode extends JPanel implements MouseListener {
    
    private GamePanel panel;
    private ArrayList<Circle> circles = new ArrayList<>();
    private Random random = new Random();
    private int score = 0;
    
    protected Timer circleTimer = new Timer(1000, (ActionEvent evt) -> {
        int x = random.nextInt(Game.Width);
        int y = random.nextInt(Game.Height);
        circles.add(new Circle(x, y, Color.RED, 30, panel.player1));
    });
    
    protected Timer timer = new Timer(10, (ActionEvent evt) -> {
        repaint();
    });
    
    public InfiniteMode(GamePanel panel) {
        this.panel = panel;
        this.addMouseListener(this);
        setBackground(Color.WHITE);
        timer.start();
        circleTimer.start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lifeCheck();
        
        g.setColor(Color.red);
        
        for(Circle circle : circles)
            g.fillOval(circle.origin.x - 30 / 2, circle.origin.y - 30 / 2, 30, 30);

        g.setColor(Color.BLACK);
        g.drawString(panel.player1.name + ": " + score, 5, 20);
    }
    
    public void lifeCheck() {
        if(panel.player1.hp <= 0) {
            panel.game.showMenu(new MainMenu(panel));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(circles.size() > 0)
            for(Circle circle : circles) {
                if(circle.contains(e.getX(), e.getY())) {
                    circles.remove(circle);
                    score++;
                }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
