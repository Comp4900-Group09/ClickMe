/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;

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
        JPanel contentPane = new JPanel( new BorderLayout() );
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder( border, "Game Panel will be below");
        contentPane.setBorder( border );
        setContentPane( contentPane );
        setVisible(true);
        addComponents();
    }
    
    public void addComponents() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem exit = new JMenuItem("Exit");
        
        gameMenu.setMnemonic(KeyEvent.VK_G);
        newGame.setMnemonic(KeyEvent.VK_N);
        exit.setMnemonic(KeyEvent.VK_E);  
        
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                 JOptionPane.showMessageDialog(Game.this,
                    "New Game Created.", "Game Message", JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            System.exit(0);
            }
        });
        
        gameMenu.add(newGame);
        gameMenu.add(exit);
        menuBar.add(gameMenu);
        this.setJMenuBar(menuBar);
    }
}
