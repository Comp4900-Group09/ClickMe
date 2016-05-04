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
    protected static int Width = 640;
    protected static int Height = 480;
    
    public Game() {
        setTitle("Prototype Game");
        setBounds(0, 0, Width, Height);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE );
        GamePanel gamePanel = new GamePanel();
        setContentPane( gamePanel );
        addComponents();
        setVisible(true);
    }
    
    public void addComponents() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");
        
        gameMenu.setMnemonic(KeyEvent.VK_G);
        newGame.setMnemonic(KeyEvent.VK_N);
        exit.setMnemonic(KeyEvent.VK_E);  
        
        newGame.addActionListener((ActionEvent event) -> {
            JOptionPane.showMessageDialog(Game.this,
                    "New Game Created.", "Game Message", JOptionPane.PLAIN_MESSAGE);
        });

        exit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        gameMenu.add(newGame);
        gameMenu.add(exit);
        menuBar.add(gameMenu);
        this.setJMenuBar(menuBar);
    }
}
