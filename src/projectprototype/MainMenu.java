package projectprototype;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MainMenu extends Menu {

    private final JButton exit;

    public MainMenu(GamePanel panel) {
        super(panel);
        this.setFocusable(true);
        this.addKeyListener(this);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton button;

        button = new JButton("Single Player");
        button.addActionListener((ActionEvent event) -> {
            this.panel.newGame();
            this.panel.timer.start();
            panel.game.showMenu(panel);
        });
        c.gridx = 0;
        c.gridy = 0;
        add(button, c);

        button = new JButton("Multiplayer");
        button.addActionListener((ActionEvent e) -> {
            panel.game.showMenu(new MultiplayerMenu(panel));
        });
        c.gridx = 0;
        c.gridy = 1;
        add(button, c);

        button = new JButton("Settings");
        button.addActionListener((ActionEvent e) -> {

            String s = (String) JOptionPane.showInputDialog(
                    this,
                    "Select window size", "Settings",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    panel.game.Resolutions,
                    panel.game.Resolutions[panel.game.index]);

            for (int i = 0; i < panel.game.Resolutions.length; i++) {
                if (panel.game.Resolutions[i].equals(s)) {
                    panel.game.index = i;
                    break;
                }
            }

            if (s != null) {
                String[] x = s.split("x");
                panel.game.Width = Integer.parseInt(x[0]);
                panel.game.Height = Integer.parseInt(x[1]);
                this.panel.setupArea(panel.game.Width, panel.game.Height);

                this.setSize(panel.game.Width, panel.game.Height);
            }
        });
        c.gridx = 0;
        c.gridy = 2;
        add(button, c);

        exit = new JButton("Exit");
        exit.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
        c.gridx = 0;
        c.gridy = 3;
        add(exit, c);
        setVisible(true);
        this.requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = Color.BLACK;
        Color color2 = Color.WHITE;
        GradientPaint gp = new GradientPaint(0, 0.2f, color1, 0.7f, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
