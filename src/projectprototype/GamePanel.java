package projectprototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class GamePanel extends JPanel implements MouseListener {

    /*Temporary players*/
    protected static Player player1;
    protected static Player player2;

    protected Timer timer = new Timer(10, (ActionEvent evt) -> {
        repaint();
    });

    protected GazePoint pointer = new GazePoint();
    /*Rectangles signifying the players area (half the screen)*/
    protected Rectangle rect1, rect2;

    //idk temp stuff
    protected Point gaze = new Point();

    protected int x, y;

    protected boolean playerInitialized = false;

    /*GamePanel constructor.*/
    public GamePanel(int width, int height) {
        setBackground(Color.WHITE);
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border);
        setBorder(border);
        setupArea(width, height);
        addMouseListener(this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player1.objects.stream().forEach((circle) -> {
            //draw player 2 objects
            g.setColor(Color.blue);
            g.fillOval(circle.origin.x - player2.size / 2, circle.origin.y - player2.size / 2, player2.size, player2.size);
        });
        player2.objects.stream().forEach((circle) -> {
            g.setColor(Color.red);
            g.fillOval(circle.origin.x - player1.size / 2, circle.origin.y - player1.size / 2, player1.size, player1.size);
        });

        g.setColor(Color.BLACK);

        g.drawString(player1.name + ": " + player1.hp, 5, 20);
        g.drawString(player2.name + ": " + player2.hp, Game.Width - 54, 20);

        g.drawLine(Game.Width / 2, 200, Game.Width / 2, Game.Height - 200);

        try {
            gaze = pointer.getCoordinates();
        } catch(Exception e) {
            
        }

         g.fillOval(gaze.x, gaze.y, 5, 5);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getButton() == 3) { //right mouse click, simulate player 2 move
            Random random = new Random();
            int x = random.nextInt(Game.Width / 2);
            int y = random.nextInt(Game.Height / 2);
            Circle circle = new Circle(x, y, player2.size, this, player1);
            player1.objects.add(circle);
        } else {
            int x = e.getX();
            int y = e.getY();
            boolean inside = false;
            for (Circle circle : player1.objects) {
                if (circle.contains(x, y)) {
                    player1.objects.remove(circle);
                    inside = true;
                    break;
                }
            }
            if (!inside && player2.objects.size() < Debug.maxCircles) {
                if (rect2.contains(x, y)) {
                    Circle circle = new Circle(e.getX(), e.getY(), player1.size, this, player2);
                    player2.objects.add(circle);
                    try {
                        Game.cclient.send(circle);
                    } catch(Exception q) {}
                }
            }
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
        rect1 = new Rectangle(0, 0, width / 2, height);
        rect2 = new Rectangle(width / 2, 0, width / 2, height);
    }

    /*Asks for names for both players.*/
    public boolean initializePlayers() {
        String name1 = JOptionPane.showInputDialog(null,
                "Player 1 please input your name.\nMax 6 chars.",
                "Player Name Input.",
                JOptionPane.QUESTION_MESSAGE);
        String name2 = JOptionPane.showInputDialog(null,
                "Player 2 please input your name.\nMax 6 chars.",
                "Player Name Input.",
                JOptionPane.QUESTION_MESSAGE);
        if(name1 != null && name2 != null && !name1.isEmpty() && !name2.isEmpty()){
        if (!banCheck(name1) && !banCheck(name2)) {
            if (name1.length() <= 6 && name2.length() <= 6) {
                this.player1 = new Player(name1);
                this.player2 = new Player(name2);
                return true;
            } else {
                return false;
            }
        } else {
            this.promptBan();
            this.newGame();
            return true;
        }
        } else{
            return false;
        }
    }

    public boolean banCheck(String name) {
        String[] bannedNames = {"gern", "nigger", "nigga", "chinese", "black"};
        for (String n : bannedNames) {
            if (name.equals(n)) {
                return true;
            }
        }
        return false;
    }

    public void promptBan() {
        JOptionPane.showMessageDialog(this, "You are banned from the game.", "Game Banned", JOptionPane.ERROR_MESSAGE);
    }

    public void newGame() {
        playerInitialized = initializePlayers();
        while (!playerInitialized) {
            playerInitialized = initializePlayers();
        }
        this.player1.objects.stream().forEach((c) -> {
            c.clearTimer();
        });
        this.player2.objects.stream().forEach((c) -> {
            c.clearTimer();
        });
        this.player1.objects.clear();
        this.player2.objects.clear();
        player1.hp = 5;
        player2.hp = 5;
    }
}
