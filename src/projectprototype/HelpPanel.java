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
            nextButton.setVisible(true);
        }
        if(index == 0)
            prevButton.setVisible(false);
    };

    protected ActionListener nextListener = (ActionEvent e) -> {
        if (index < images.size()-1) {
            index++;
            ic = new ImageIcon(images.get(index));
            imageSpot.setIcon(ic);
            prevButton.setVisible(true);
        }
        if(index == images.size()-1)
            nextButton.setVisible(false);
    };

    /**
     * Creates new form HelpPanel
     */
    public HelpPanel(GamePanel panel) {
        super(panel);
        images = new ArrayList();
        for (int i = 1; i <= 3; i++) {
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
        prevButton.setVisible(false);

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
