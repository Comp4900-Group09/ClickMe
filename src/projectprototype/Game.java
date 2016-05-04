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
public class Game {
    protected static int Width = 640;
    protected static int Height = 480;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prototype Game");
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setMnemonic(KeyEvent.VK_N);
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic(KeyEvent.VK_E);    
        
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                 JOptionPane.showMessageDialog(frame,
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
        frame.setJMenuBar(menuBar);
        
        frame.setBounds(0, 0, Width, Height);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE );
        JPanel contentPane = new JPanel( new BorderLayout() );
        Border border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder( border, "Game Panel will be below");
        contentPane.setBorder( border );
        frame.setContentPane( contentPane );
        frame.setVisible(true);
    }
    
}
