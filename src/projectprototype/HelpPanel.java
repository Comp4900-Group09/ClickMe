/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;

/**
 *
 * @author juven1996
 */
public class HelpPanel extends Menu {

    private JLabel imageSpot;
    private JButton nextButton;
    private JButton prevButton;
    private ArrayList<Image> images;
    protected static int index = 0;
    protected ImageIcon ic;
    protected ActionListener prevListener = (ActionEvent e) -> {
        if (index > 0) {
            index--;
            ic = new ImageIcon(images.get(index));
            imageSpot.setIcon(ic);
        }
    };

    protected ActionListener nextListener = (ActionEvent e) -> {
        if (index < 5) {
            index++;
            ic = new ImageIcon(images.get(index));
            imageSpot.setIcon(ic);
        }
    };

    /**
     * Creates new form HelpPanel
     */
    public HelpPanel(GamePanel panel) {
        super(panel);
        images = new ArrayList();
        for (int i = 1; i <= 6; i++) {
            Image image = Toolkit.getDefaultToolkit().getImage("images/help" + i + ".png");
            image = image.getScaledInstance(640, 430, Image.SCALE_DEFAULT);
            images.add(image);
        }
        initComponents();
    }

    private void initComponents() {

        imageSpot = new JLabel();
        ic = new ImageIcon(images.get(index));
        imageSpot.setIcon(ic);
        prevButton = new JButton();
        prevButton.addActionListener(prevListener);
        nextButton = new JButton();
        nextButton.addActionListener(nextListener);

        prevButton.setText("Previous");

        nextButton.setText("Next");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(imageSpot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(prevButton)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 479, Short.MAX_VALUE)
                                        .addComponent(nextButton)))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(imageSpot, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(prevButton)
                                .addComponent(nextButton))
                        .addContainerGap())
        );
    }
}
