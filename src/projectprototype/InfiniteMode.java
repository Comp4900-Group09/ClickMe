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
    private Powerup power;
    private Random random = new Random();
    private int score = 0;
    private final int POWERUPCHANCE = 60000;
    //private Powerup[] powers = {new SizeIncrease(panel), new LongTimer(panel)}; //maybe use to randomly select powerup
    
    protected Timer circleTimer = new Timer(1000, (ActionEvent evt) -> {
        int x = random.nextInt(Game.Width);
        int y = random.nextInt(Game.Height);
        int powerup = random.nextInt(65535);
        if(powerup > POWERUPCHANCE) {
            if(powerup%2 == 0)
                power = new SizeIncrease(x, y, panel);
            else
                power = new LongTimer(x, y, panel);
        }
        else
            panel.player1.objects.add(new Circle(x, y, Color.RED, panel.player1.size, panel.player1));
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
        
        for(Circle circle : panel.player1.objects) {
            g.setColor(circle.color);
            g.fillOval(circle.origin.x - panel.player1.size/ 2, circle.origin.y - panel.player1.size / 2, panel.player1.size, panel.player1.size);
        }
        
        if(power != null) {
            g.setColor(power.color);
            g.fillOval(power.origin.x-panel.player1.size/2, power.origin.y-panel.player1.size/2, 30, 30);
        }

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
        if(panel.player1.objects.size() > 0)
            for(Circle circle : new ArrayList<>(panel.player1.objects)) {
                if(circle.contains(e.getX(), e.getY())) {
                    panel.player1.objects.remove(circle);
                    score++;
                    return;
                }
            }
        if(power != null && power.contains(e.getX(), e.getY())) {
            power.apply();
            power = null;
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
