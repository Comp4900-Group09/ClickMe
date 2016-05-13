package projectprototype;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

/**
 *
 * @author Matthew
 */
public class Menu extends JPanel implements KeyListener {

    public GamePanel panel;

    public Menu(GamePanel panel) {
        this.panel = panel;
        this.requestFocusInWindow();
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
