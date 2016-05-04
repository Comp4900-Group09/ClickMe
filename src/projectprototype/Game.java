/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author juven1996
 */
public class Game extends JFrame {

    protected static int Width = 1240;
    protected static int Height = 1040;
    protected Object[] Resolutions = {"640x480", "1024x720", "1280x1040"};
    protected GamePanel gamePanel;

    public Game() {
        setTitle("Prototype Game");
        setResizable(false);
        setBounds(0, 0, Width, Height);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        setContentPane(gamePanel);
        addComponents();
        setVisible(true);
    }
    
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
            Game.this.gamePanel.repaint();
        });

        exit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        settings.addActionListener((ActionEvent event) -> {
            
            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Select window size", "Settings",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Resolutions,
                    Resolutions[0]);

            if ((s != null) && (s.length() > 0)) {
                String[] x = s.split("x");
                Width = Integer.parseInt(x[0]);
                Height = Integer.parseInt(x[1]);
                
                this.setSize(Width,Height);
                repaint();
                return;
            }
        });

        gameMenu.add(newGame);
        gameMenu.add(settings);
        gameMenu.add(exit);
        menuBar.add(gameMenu);
        this.setJMenuBar(menuBar);
    }

}
