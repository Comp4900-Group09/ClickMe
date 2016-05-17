package projectprototype;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends JFrame {

    protected static int Width = 1280;
    protected static int Height = 720;

    /*Index used to keep track of resolution. Default is 0.*/
    protected int index = 0;

    protected JMenuItem mainMenu;
    protected JMenuBar menuBar;

    /*Reference to GamePanel.*/
    protected GamePanel panel;

    protected Server sserver;
    protected Client cclient;

    protected Player player1;
    protected Player player2;

    protected boolean playerInitialized = false;

    protected JLabel player1Label, player2Label = new JLabel();

    /*Game constructor.*/
    public Game() {
        ImageIcon img = new ImageIcon("./images/logo.png");
        this.setIconImage(img.getImage());
        panel = new GamePanel(Width, Height, this);
        setTitle("Prototype Game");
        setResizable(false);
        setBounds(0,0,Width, Height);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setContentPane(new MainMenu(panel));
        //addComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showMenu(JPanel panel) {
        this.getContentPane().removeAll();
        this.setContentPane(panel);
        panel.revalidate();
        panel.repaint();
    }

//    /*Adds components to the screen.*/
//    public void addComponents() {
//        menuBar = new JMenuBar();
//        JMenu gameMenu = new JMenu("Game");
//
//        mainMenu = new JMenuItem("Main Menu");
//        JMenuItem exit = new JMenuItem("Exit");
//
//        gameMenu.setMnemonic(KeyEvent.VK_G);
//        mainMenu.setMnemonic(KeyEvent.VK_N);
//        exit.setMnemonic(KeyEvent.VK_E);
//
//        mainMenu.addActionListener((ActionEvent event) -> {
//            if (sserver != null) {
//                try {
//                    sserver.disconnect();
//                } catch (IOException ex) {
//                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            if (cclient != null) {
//                try {
//                    cclient.disconnect();
//                } catch (IOException ex) {
//                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//            this.panel.timer.stop();
//            this.panel.clearCircles();
//            this.getContentPane().removeAll();
//            this.setContentPane(new MainMenu(panel));
//            this.revalidate();
//            this.repaint();
//        });
//
//        exit.addActionListener((ActionEvent event) -> {
//            System.exit(0);
//        });
//
//        gameMenu.add(mainMenu);
//        gameMenu.add(exit);
//        menuBar.add(gameMenu);
//        menuBar.add(debug());
//        this.setJMenuBar(menuBar);
//    }
//
//    public JMenu debug() {
//        JMenu debug = new JMenu("Debug");
//        JCheckBoxMenuItem timer = new JCheckBoxMenuItem("Timer", true);
//        JCheckBoxMenuItem limit = new JCheckBoxMenuItem("Limit", true);
//        debug.add(timer);
//        debug.add(limit);
//        timer.addItemListener(new ItemHandler());
//        limit.addItemListener(new ItemHandler());
//        return debug;
//
//    }

    public class ItemHandler implements ItemListener {

        public void itemStateChanged(ItemEvent e) {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getItem();
            if (item.getText().equals("Timer")) {
                Debug.doTime = !Debug.doTime;
            } else if (item.getText().equals("Limit")) {
                if (Debug.maxCircles == 3) {
                    Debug.maxCircles = 1000;
                } else {
                    Debug.maxCircles = 3;
                }
            }
        }
    }
}
