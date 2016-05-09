package projectprototype;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.Border;

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
    protected GamePanel panel;

    protected static Server sserver;
    protected static Client cclient;

    protected boolean playerInitialized = false;

    /*Game constructor.*/
    public Game() {
        panel = new GamePanel(Width, Height);

        setTitle("Prototype Game");
        setResizable(false);
        setBounds(0, 0, Width, Height);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        //setContentPane(panel);
        setContentPane(showMenu());
        addComponents();
        setVisible(true);
    }

    public JPanel multiplayerMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton button;

        final JButton host = new JButton("Host a game");
        host.addActionListener((ActionEvent e) -> {
            host.setEnabled(false);
            sserver = new Server(this.panel);
            if (sserver.clientConnected) {
                host.setEnabled(true);
            }
            /*this.getContentPane().removeAll();
            this.setContentPane(multiplayerLobby());
            this.revalidate();
            this.repaint();*/
        });
        c.gridx = 0;
        c.gridy = 0;
        panel.add(host, c);

        button = new JButton("Join a game");
        button.addActionListener((ActionEvent e) -> {
            String address = getServerAddress();
            cclient = new Client(address);
        });
        c.gridx = 0;
        c.gridy = 1;
        panel.add(button, c);

        button = new JButton("Lobby Test");
        button.addActionListener((ActionEvent e) -> {
            /*this.getContentPane().removeAll();
            this.setContentPane(multiplayerLobby());
            this.revalidate();
            this.repaint();*/
        });
        c.gridx = 0;
        c.gridy = 2;
        panel.add(button, c);

        button = new JButton("Back to Menu");
        button.addActionListener((ActionEvent e) -> {
            this.getContentPane().removeAll();
            this.setContentPane(showMenu());
            this.revalidate();
            this.repaint();
        });
        c.gridx = 0;
        c.gridy = 3;
        panel.add(button, c);

        return panel;
    }

    public JPanel showMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton button;

        button = new JButton("Single Player");
        button.addActionListener((ActionEvent event) -> {
            this.panel.newGame();
            this.panel.timer.start();
            this.getContentPane().removeAll();
            this.setContentPane(this.panel);
            this.revalidate();
            this.repaint();
        });
        c.gridx = 0;
        c.gridy = 0;
        panel.add(button, c);

        button = new JButton("Multiplayer");
        button.addActionListener((ActionEvent e) -> {
            this.getContentPane().removeAll();
            this.setContentPane(multiplayerMenu());
            this.revalidate();
            this.repaint();
        });
        c.gridx = 0;
        c.gridy = 1;
        panel.add(button, c);

        button = new JButton("Settings");
        button.addActionListener((ActionEvent e) -> {

            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Select window size", "Settings",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    Resolutions,
                    Resolutions[index]);

            for (int i = 0; i < Resolutions.length; i++) {
                if (Resolutions[i].equals(s)) {
                    index = i;
                    break;
                }
            }

            if (s != null) {
                String[] x = s.split("x");
                Width = Integer.parseInt(x[0]);
                Height = Integer.parseInt(x[1]);
                this.panel.setupArea(Width, Height);

                this.setSize(Width, Height);
            }
        });
        c.gridx = 0;
        c.gridy = 2;
        panel.add(button, c);

        button = new JButton("Exit");
        button.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        c.gridx = 0;
        c.gridy = 3;
        panel.add(button, c);
        return panel;
    }

    /*Adds components to the screen.*/
    public void addComponents() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");

        JMenuItem mainMenu = new JMenuItem("Main Menu");
        JMenuItem exit = new JMenuItem("Exit");

        gameMenu.setMnemonic(KeyEvent.VK_G);
        mainMenu.setMnemonic(KeyEvent.VK_N);
        exit.setMnemonic(KeyEvent.VK_E);

        mainMenu.addActionListener((ActionEvent event) -> {
            this.panel.timer.stop();
            this.panel.clearCircles();
            this.getContentPane().removeAll();
            this.setContentPane(showMenu());
            this.revalidate();
            this.repaint();
        });

        exit.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        gameMenu.add(mainMenu);
        gameMenu.add(exit);
        menuBar.add(gameMenu);
        menuBar.add(debug());
        menuBar.add(server());
        this.setJMenuBar(menuBar);
    }

    public String getServerAddress() {
        JTextField addressInput = new JTextField(15);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Please Enter Server Address or \"localhost\" for local server:"));
        myPanel.add(addressInput);

        String address = "";

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Server settings.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            address = addressInput.getText();
            System.out.println("Server Address: " + addressInput.getText());
        }
        return address;
    }

    public JMenu server() {
        JMenu server = new JMenu("Server");
        JMenuItem serverItem = new JMenuItem("Server");
        JMenuItem clientItem = new JMenuItem("Client");

        serverItem.addActionListener((ActionEvent event) -> {
            sserver = new Server(panel);
        });

        clientItem.addActionListener((ActionEvent event) -> {
            String address = getServerAddress();
            cclient = new Client(address);
        });
        server.add(serverItem);
        server.add(clientItem);
        return server;
    }

    public JMenu debug() {
        JMenu debug = new JMenu("Debug");
        JCheckBoxMenuItem timer = new JCheckBoxMenuItem("Timer", true);
        JCheckBoxMenuItem limit = new JCheckBoxMenuItem("Limit", true);
        debug.add(timer);
        timer.addItemListener(new ItemHandler());
        limit.addItemListener(new ItemHandler());
        return debug;

    }

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
