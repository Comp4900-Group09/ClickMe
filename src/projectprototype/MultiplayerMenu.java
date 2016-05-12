package projectprototype;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
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
            panel.game.showMenu(new Lobby(panel.player1, null, panel));
        });
        c.gridx = 0;
        c.gridy = 0;
        add(host, c);

        button = new JButton("Join a game");
        button.addActionListener((ActionEvent e) -> {
            String address = getServerAddress();
            panel.game.cclient = new Client(address, this.panel);
            removeAll();
            panel.game.showMenu(new Lobby(panel.player1, panel.player2, panel));
            this.revalidate();
            this.repaint();
        });
        c.gridx = 0;
        c.gridy = 1;
        add(button, c);

        button = new JButton("Back to Menu");
        button.addActionListener((ActionEvent e) -> {
            removeAll();
            panel.game.showMenu(new MainMenu(panel));
            this.revalidate();
            this.repaint();
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
}
