package projectprototype;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

public class Lobby extends Menu {
    
    public JLabel player1Label, player2Label;
    public JTextArea chat;

    public Lobby(Player serverPlayer, Player clientPlayer, GamePanel panel) {
        super(panel);
        player1Label = new JLabel(serverPlayer.name);
        player2Label = clientPlayer == null ? new JLabel("") : new JLabel(clientPlayer.name);
        JCheckBox ready1 = new JCheckBox();
        JCheckBox ready2 = new JCheckBox();
        JScrollPane scrollbar = new JScrollPane();
        chat = new JTextArea();
        JTextField chatInput = new JTextField();
        chatInput.addActionListener((ActionEvent e) -> {
            chat.append(this.panel.player1.name + ": " + chatInput.getText() + "\n");
            if (this.panel.game.sserver != null) {
                this.panel.game.sserver.send(chatInput.getText());
            } else if (this.panel.game.cclient != null) {
                this.panel.game.cclient.send(chatInput.getText());
            }
            chatInput.setText("");
        });
        JLabel panelTitle = new JLabel();
        JSeparator line = new JSeparator();
        JSeparator line2 = new JSeparator();
        JButton startGame = new JButton();
        JButton leaveGame = new JButton();

        player1Label.setBorder(new javax.swing.border.MatteBorder(null));
        player2Label.setBorder(new javax.swing.border.MatteBorder(null));

        ready1.setText("ready");
        ready2.setText("ready");

        chat.setColumns(20);
        chat.setLineWrap(true);
        chat.setRows(5);
        chat.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        chat.setEditable(false);
        scrollbar.setViewportView(chat);

        chatInput.setToolTipText("Chat goes here");

        panelTitle.setText("Lobby");

        startGame.setText("Start Game");
        startGame.addActionListener((ActionEvent e) -> {
            this.panel.newGame(this.panel.player1, this.panel.player2);
            this.panel.timer.start();
            panel.game.showMenu(panel);
        });

        leaveGame.setText("Leave Game");
        leaveGame.addActionListener((ActionEvent e) -> {
            if (this.panel.game.sserver != null) {
                try {
                    this.panel.game.sserver.disconnect();
                } catch (Exception ex) {
                    System.err.println("Failed to close sockets.");
                }
            } else if (this.panel.game.cclient != null) {
                try {
                    this.panel.game.cclient.disconnect();
                } catch (Exception ex) {
                    System.err.println("Failed to close sockets.");
                }
            }
            panel.game.showMenu(new MultiplayerMenu(panel));
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(line2, GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(line)
                                                                                .addComponent(player1Label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(player2Label, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE))
                                                                        .addGap(18, 18, 18)
                                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(ready1)
                                                                                .addComponent(ready2))
                                                                        .addGap(62, 62, 62)
                                                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(startGame, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(leaveGame)))
                                                                .addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE))
                                                .addComponent(scrollbar)
                                                .addComponent(chatInput))))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(line2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(player1Label, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ready1)
                                .addComponent(startGame))
                        .addGap(4, 4, 4)
                        .addComponent(line, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(player2Label, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                                .addComponent(ready2)
                                .addComponent(leaveGame))
                        .addGap(18, 18, 18)
                        .addComponent(scrollbar, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        add(player1Label);
        add(player2Label);
        add(ready1);
        add(ready2);
        add(scrollbar);
        add(chatInput);
        add(panelTitle);
        add(line);
        add(line2);
        add(startGame);
        add(leaveGame);
    }
}
