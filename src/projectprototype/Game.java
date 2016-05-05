package projectprototype;

import javax.swing.*;
import java.awt.event.*;

public class Game extends JFrame {

    /*Width of the window. Default is 640.*/
    protected static int Width = 640;
    
    /*Height of the window. Default is 480.*/
    protected static int Height = 480;
    
    /*Index used to keep track of resolution. Default is 0.*/
    protected int index = 0;
    
    /*Array of supported resolutions.*/
    protected String[] Resolutions = {"640x480", "1024x768", "1280x1024"};
    
    /*Reference to GamePanel.*/
    protected GamePanel gamePanel;

    /*Game constructor.*/
    public Game() {
        setTitle("Prototype Game");
        setResizable(false);
        setBounds(0, 0, Width, Height);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        gamePanel = new GamePanel(Width, Height);
        setContentPane(gamePanel);
        addComponents();
        setVisible(true);
    }
    
    /*Adds components to the screen.*/
    public void addComponents() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem settings = new JMenuItem("Settings");

        gameMenu.setMnemonic(KeyEvent.VK_G);
        newGame.setMnemonic(KeyEvent.VK_N);
        exit.setMnemonic(KeyEvent.VK_E);
        settings.setMnemonic(KeyEvent.VK_S);

        newGame.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(Game.this,
                    "New Game Created.", "Game Message", JOptionPane.PLAIN_MESSAGE);
            Game.this.gamePanel.objectList.clear();
            Game.this.gamePanel.resetGame();
            Game.this.gamePanel.repaint();
        });

        exit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        
        settings.addActionListener((ActionEvent event) -> {
            
            String s = (String)JOptionPane.showInputDialog(
                    this,
                    "Select window size", "Settings",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Resolutions,
                    Resolutions[index]);
            
            for(int i = 0; i < Resolutions.length; i++) {
                if(Resolutions[i].equals(s)) {
                    index = i;
                    break;
                }
            }

            if (s != null) {
                String[] x = s.split("x");
                Width = Integer.parseInt(x[0]);
                Height = Integer.parseInt(x[1]);
                gamePanel.setupArea(Width, Height);
                
                this.setSize(Width, Height);
            }
        });

        gameMenu.add(newGame);
        gameMenu.add(settings);
        gameMenu.add(exit);
        menuBar.add(gameMenu);
        this.setJMenuBar(menuBar);
    }
}
