package projectprototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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
    }
    
    public void setPlayer(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lifeCheck();
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
        } catch (Exception e) {

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
                        createCircle(circle);
                    } catch (Exception q) {
                    }
                }
            }
        }
        repaint();
    }

    public void createCircle(Circle circle) throws IOException {
        if (Game.cclient != null) {
            Game.cclient.send(circle);
        } else if (Game.sserver != null) {
            Game.sserver.send(circle);
        }
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

    public void lifeCheck() {
        if (this.player1.hp == 0) {
            this.timer.stop();
            playerInitialized = false;
            JOptionPane.showMessageDialog(this, "The winner is " + this.player2.name + ".", "Game Ended.", JOptionPane.OK_OPTION);
        }
        if (this.player2.hp == 0) {
            this.timer.stop();
            playerInitialized = false;
            JOptionPane.showMessageDialog(this, "The winner is " + this.player1.name + ".", "Game Ended.", JOptionPane.OK_OPTION);
        }
        
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
        if (name1 != null && name2 != null && !name1.isEmpty() && !name2.isEmpty()) {
            if (name1.length() <= 6 && name2.length() <= 6) {
                if (!banCheck(name1) && !banCheck(name2)) {
                    this.player1 = new Player(name1);
                    this.player2 = new Player(name2);
                    return true;
                } else {
                    this.promptBan();
                    this.newGame();
                    return true;
                }
            } else {
                newGame();
                return false;
            }
        }
        return false;
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

    public void clearCircles() {
        this.player1.objects.stream().forEach((c) -> {
            c.clearTimer();
        });
        this.player2.objects.stream().forEach((c) -> {
            c.clearTimer();
        });
        this.player1.objects.clear();
        this.player2.objects.clear();
    }

    public void newGame() {
        playerInitialized = initializePlayers();
        while (!playerInitialized) {
            playerInitialized = initializePlayers();
        }
        clearCircles();
        player1.hp = 5;
        player2.hp = 5;
        this.timer.start();
    }
}
