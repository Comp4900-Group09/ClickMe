package projectprototype;

import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeEvent;

public class SettingsMenu extends Menu {

    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JLabel jLabel1;
    private JSlider jSlider1;

    
    public SettingsMenu(GamePanel panel) {
        super(panel);
        initComponents();
    }
                      
    private void initComponents() {
        jLabel1 = new JLabel();
        jSlider1 = new JSlider();
        jSlider1.setValue((int)(audio.mediaPlayer.getVolume() * 100));
        jSlider1.addChangeListener((ChangeEvent e) -> {
            JSlider s = (JSlider)e.getSource();
            audio.mediaPlayer.setVolume(s.getValue()/100.0);
        });
        jCheckBox1 = new JCheckBox();
        jCheckBox1.addActionListener((ActionEvent e) -> {
            audio.mediaPlayer.setVolume(jCheckBox1.isSelected() ? 0.0 : jSlider1.getValue()/100.0);
        });
        jCheckBox2 = new JCheckBox();

        jLabel1.setText("Set Volume");

        jCheckBox1.setText("Mute Music");

        jCheckBox2.setText("Mute Sound Effects");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jCheckBox2)
                                .addComponent(jLabel1)
                                .addComponent(jSlider1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBox1))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)
                        .addContainerGap(17, Short.MAX_VALUE))
        );
    }                  
    
}
