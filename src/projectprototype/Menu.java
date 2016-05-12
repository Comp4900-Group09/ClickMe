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
