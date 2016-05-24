package projectprototype;

import java.awt.Color;
import java.awt.Font;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

public class GamePanel extends JPanel implements MouseListener, KeyListener {

    /*Temporary players*/
    protected Player player1;
    protected Player player2;

    public Image image = Toolkit.getDefaultToolkit().getImage("images/bg.png");

    protected Timer timer = new Timer(10, (ActionEvent evt) -> {
        repaint();
    });

    protected GazePoint pointer = new GazePoint();
    /*Rectangles signifying the players area (half the screen)*/
    protected Rectangle rect1, rect2;

    //idk temp stuff
    protected Point gaze = new Point();

    protected int x, y;

    protected Game game;

    protected boolean playerInitialized = false;

    /*GamePanel constructor.*/
    public GamePanel(int width, int height, Game game) {
        this.game = game;
        setBackground(Color.WHITE);
        image = image.getScaledInstance(Game.Width, Game.Height-20, Image.SCALE_DEFAULT);
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border);
        setBorder(border);
        setupArea(width, height);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        addMouseListener(this);
    }

    public void setPlayer(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        lifeCheck();
        player1.objects.stream().forEach((circle) -> {
            //draw player 2 objects
            g.setColor(circle.color);
            g.fillOval(circle.origin.x - player2.size / 2, circle.origin.y - player2.size / 2, player2.size, player2.size);
        });
        player2.objects.stream().forEach((circle) -> {
            g.setColor(circle.color);
            g.fillOval(circle.origin.x - player1.size / 2, circle.origin.y - player1.size / 2, player1.size, player1.size);
        });

        g.setColor(Color.BLACK);

        g.drawString(player1.name + ": " + player1.hp, 5, 20);
        g.drawString(player2.name + ": " + player2.hp, Game.Width - player2.name.length()-70, 20);
        
        Font myFont = new Font ("Courier New", 5, 30);
        g.setFont(myFont);
        g.drawString("MADE BY MATTHEW AND JUVEN ONLY", Game.Width/4, Game.Height/2);

        g.drawLine(Game.Width / 2, 200, Game.Width / 2, Game.Height - 200);

        try {
            gaze = pointer.getCoordinates();
        } catch (Exception e) {

        }

        g.fillOval(gaze.x, gaze.y, 5, 5);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_1) {
            try {
                gaze = pointer.getCoordinates();
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

    public void sendCircle(Circle circle) throws IOException {
        if (game.cclient != null) {
            game.cclient.send(circle);
        } else if (game.sserver != null) {
            game.sserver.send(circle);
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

    public void stopGame(int playerNum) {
        if (playerNum == 1) {
            JOptionPane.showMessageDialog(this, "The winner is " + this.player2.name + ".", "Game Ended.", JOptionPane.OK_OPTION);
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
        } else if (playerNum == 2) {
            JOptionPane.showMessageDialog(this, "The winner is " + this.player1.name + ".", "Game Ended.", JOptionPane.OK_OPTION);
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
        }
        game.showMenu(new MainMenu(this));
    }

    public void lifeCheck() {
        if (this.player1.hp == 0) {
            this.timer.stop();
            playerInitialized = false;
            stopGame(1);
        }
        else if (this.player2.hp == 0) {
            this.timer.stop();
            playerInitialized = false;
            stopGame(2);
        }
    }
    
    public String promptPlayer(int playerNum) {
        String name1 = JOptionPane.showInputDialog(null,
            "Player " + playerNum + " " +  "please input your name.\nMax 8 chars.",
            "Player Name Input.",
            JOptionPane.QUESTION_MESSAGE);
        while(banCheck(name1)) {
            promptBan();
            name1 = JOptionPane.showInputDialog(null,
            "Player " + playerNum + " " +  "please input your name.\nMax 8 chars.",
            "Player Name Input.",
            JOptionPane.QUESTION_MESSAGE);
        }
        if(name1 != null && name1.length() <= 8 && !name1.isEmpty())
            return name1;
        return null;
    }

    /*Asks for names for both players.*/
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
        this.setVisible(true);
        playerInitialized = initializePlayers();
        while (!playerInitialized) {
            playerInitialized = initializePlayers();
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

    @Override
    public void keyReleased(KeyEvent arg0) {
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }
}
