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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MultiplayerMenu extends Menu {

    public MultiplayerMenu(GamePanel panel) {
        super(panel);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton button;

        final JButton host = new JButton("Host a game");
        host.addActionListener((ActionEvent e) -> {
            panel.game.sserver = new Server(this.panel);
        });
        c.gridx = 0;
        c.gridy = 0;
        add(host, c);

        button = new JButton("Join a game");
        button.addActionListener((ActionEvent e) -> {
            String address = getServerAddress();
            panel.game.cclient = new Client(address, this.panel);
        });
        c.gridx = 0;
        c.gridy = 1;
        add(button, c);

        button = new JButton("Back to Menu");
        button.addActionListener((ActionEvent e) -> {
            panel.game.showMenu(new MainMenu(panel));
        });
        c.gridx = 0;
        c.gridy = 2;
        add(button, c);
    }

    public String getServerAddress() {
        JTextField addressInput = new JTextField(15);
        addressInput.setText("localhost");
        addressInput.grabFocus();
        addressInput.requestFocus();
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Please Enter Server Address or \"localhost\" for local server:"));
        myPanel.add(addressInput);
        addressInput.requestFocusInWindow();
        String address = "";

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Server settings.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            address = addressInput.getText();
            System.out.println("Server Address: " + addressInput.getText());
        }
        return address;
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
