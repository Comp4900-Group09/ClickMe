package projectprototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class GamePanel extends JPanel implements MouseListener, KeyListener {

    /*Players*/
    protected Player player1;
    protected Player player2;

    /*Background image*/
    public Image image = Toolkit.getDefaultToolkit().getImage("images/bg.png");

    /*Timer to repaint the screen to show new gazepoint position*/
    protected Timer timer = new Timer(10, (ActionEvent evt) -> {
        repaint();
    });

    /*Opens a socket to the Gaxepoint*/
    protected GazePoint pointer = new GazePoint();
    
    /*Rectangles signifying the players area (half the screen)*/
    protected Rectangle rect1, rect2;

    /*Point on the screen user is looking at*/
    protected Point gaze = new Point();

    protected int x, y;

    /*Reference to Game*/
    protected Game game;

    protected int checkRadius = 100;

    /*GamePanel constructor.*/
    public GamePanel(int width, int height, Game game) {
        this.game = game;
        setBackground(Color.WHITE);
        image = image.getScaledInstance(Game.Width, Game.Height - 20, Image.SCALE_DEFAULT);
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border);
        setBorder(border);
        setupArea(width, height);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lifeCheck();
        player1.objects.stream().forEach((circle) -> {
            g.setColor(circle.color);
            g.fillOval(circle.origin.x - player2.size / 2, circle.origin.y - player2.size / 2, player2.size, player2.size);
        });
        
        player2.objects.stream().forEach((circle) -> {
            g.setColor(circle.color);
            g.fillOval(circle.origin.x - player1.size / 2, circle.origin.y - player1.size / 2, player1.size, player1.size);
        });

        g.setColor(Color.BLACK);

        g.drawString(player1.name + ": " + player1.hp, 5, 20);
        g.drawString(player2.name + ": " + player2.hp, Game.Width - player2.name.length() - 70, 20);

        g.drawLine(Game.Width / 2, 200, Game.Width / 2, Game.Height - 200);

        try {
            gaze = pointer.getCoordinates();
            aimAssist();
        } catch (Exception e) {

        }
        g.fillOval(gaze.x, gaze.y, 5, 5);

    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_1) {
            try {
                gaze = pointer.getCoordinates();
                aimAssist();
            } catch (Exception e) {

            }
            int x = gaze.x;
            int y = gaze.y;
            if (player1.objects.size() < Debug.maxCircles) {
                if (rect1.contains(x, y)) {
                    Circle circle = new Circle(x, y, Color.blue, player2.size, player1);
                    player1.objects.add(circle);
                    try {
                        sendCircle(circle);
                    } catch (Exception q) {
                    }
                }
            }
        }
        if (arg0.getKeyCode() == KeyEvent.VK_2) {
            try {
                gaze = pointer.getCoordinates();
                aimAssist();
            } catch (Exception e) {

            }
            int x = gaze.x;
            int y = gaze.y;
            for (Circle circle : player2.objects) {
                if (circle.contains(x, y)) {
                    player2.objects.remove(circle);
                    break;
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        boolean inside = false;
        int x = e.getX();
        int y = e.getY();
        inside = false;
        for (Circle circle : player1.objects) {
            if (circle.contains(x, y)) {
                player1.objects.remove(circle);
                inside = true;
                try {
                    sendCircle(circle);
                } catch (Exception q) {
                    q.printStackTrace();
                }
                break;
            }
        }
        if (!inside && player2.objects.size() < Debug.maxCircles) {
            if (rect2.contains(x, y)) {
                Circle circle = new Circle(e.getX(), e.getY(), Color.red, player1.size, player2);
                player2.objects.add(circle);
                try {
                    sendCircle(circle);
                } catch (Exception q) {
                    q.printStackTrace();
                }
            }
        }
        repaint();
    }

    /**Sends a circle over the socket to other players*/
    public void sendCircle(Circle circle) throws IOException {
        if (game.cclient != null) {
            game.cclient.send(circle);
        } else if (game.sserver != null) {
            game.sserver.send(circle);
        }
    }

    public void clear() {
        removeAll();
        repaint();
    }

    /**Sets each rectangle to half the screen*/
    public void setupArea(int width, int height) {
        rect1 = new Rectangle(0, 0, width / 2, height);
        rect2 = new Rectangle(width / 2, 0, width / 2, height);
    }

    /**Stops the game showing a win screen and redisplays main menu*/
    public void stopGame() {
        JOptionPane.showMessageDialog(this, "The winner is " + (player1.hp == 0 ? player2.name : player1.name) + ".", "Game Ended.", JOptionPane.OK_OPTION);
            if (game.sserver != null) {
                try {
                    game.sserver.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (game.cclient != null) {
                try {
                    game.cclient.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            game.showMenu(new MainMenu(this));
    }

    /**Checks if each user still has life remaining*/
    public void lifeCheck() {
        if (player1.hp == 0 || player2.hp == 0) {
            this.timer.stop();
            stopGame();
        }
    }

    /**Prompts the user to enter player name*/
    public String promptPlayer(int playerNum) {
        String name;
        do {
            name = JOptionPane.showInputDialog(null,
            "Player " + playerNum + " " + "please input your name.\nMax 8 chars.",
            "Player Name Input.",
            JOptionPane.QUESTION_MESSAGE);
        } while (!validateName(name));
        
        return name;
    }

    /**Asks for names for both players.*/
    public boolean initializePlayers() {
        String name1 = promptPlayer(1);
        String name2 = promptPlayer(2);
        if (name1 != null && name2 != null) {
            this.player1 = new Player(name1);
            this.player2 = new Player(name2);
            return true;
        } else {
            this.newGame();
            return false;
        }
    }

    /**Returns false if name is invalid, true if valid*/
    public boolean validateName(String name) {
        if(name == null || name.length() >= 8 || name.isEmpty())
            return false;
        String[] bannedNames = {"gern", "nigger", "nigga", "chinese", "black"};
        for (String n : bannedNames) {
            if (name.equals(n)) {
                return false;
            }
        }
        return true;
    }

    /**Currently unused*/
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
        this.setVisible(true);
        while (!initializePlayers()) {
            initializePlayers();
        }
        clearCircles();
        player1.hp = 5;
        player2.hp = 5;
        this.timer.start();
    }

    public void newGame(Player player, Player player2) {
        this.setVisible(true);
        try {
            this.player1 = player;
            this.player2 = player2;
        } catch (Exception e) {

        }
        clearCircles();
        this.player1.hp = 5;
        this.player2.hp = 5;
        this.timer.start();
    }

    private void aimAssist() {
        // check if area around point is in circles
        ArrayList<Point> circlePoints = new ArrayList<Point>();
        Circle circ = new Circle(gaze);
        for (x = gaze.x - checkRadius; x < gaze.x + checkRadius; x++) {
            for (y = gaze.y - checkRadius; y < gaze.y + checkRadius; y++) {
                if (circ.contains(x, y, checkRadius)) {
                    for (Circle circle : player1.objects) {
                        if (circle.contains(x, y, player1.size)) {
                            circlePoints.add(circle.origin);
                            break;
                        }
                    }
                    for (Circle circle : player2.objects) {
                        if (circle.contains(x, y, player2.size)) {
                            circlePoints.add(circle.origin);
                            break;
                        }
                    }
                }
            }
        }

        // if one snap into middle of it
        // if there are more then one 
        // snap into middle on closest one
        if (circlePoints.size() > 0) {
            Point min = null;
            int minimumDif = Integer.MAX_VALUE;
            for (int index = 0; index < circlePoints.size(); index++) {
                if (Math.sqrt(Math.pow(Math.abs(pointer.checkPoint.x - circlePoints.get(index).x), 2) + Math.pow(Math.abs(pointer.checkPoint.y - circlePoints.get(index).y), 2)) < minimumDif) {
                    min = circlePoints.get(index);
                }
            }
            gaze = min;
            pointer.setCheckPoint(gaze);
        } else // if no circles are in the area 
        // check if point is outside last checkpoint range
        if (Math.sqrt(Math.pow(Math.abs(pointer.checkPoint.x - gaze.x), 2) + Math.pow(Math.abs(pointer.checkPoint.y - gaze.y), 2)) > checkRadius) {
            pointer.setCheckPoint(gaze);
        } else {
            gaze = pointer.checkPoint;
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
    @Override
    public void keyReleased(KeyEvent arg0) {}
    @Override
    public void keyTyped(KeyEvent arg0) {}
}
