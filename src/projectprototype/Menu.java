package projectprototype;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.embed.swing.JFXPanel;
import javax.swing.JPanel;

/**
 *
 * @author Matthew
 */
public class Menu extends JPanel implements KeyListener {

    public GamePanel panel;
    private static JFXPanel jfx = new JFXPanel();
    protected static Audio audio = new Audio("bgmusic.mp3");

    public Menu(GamePanel panel) {
        this.panel = panel;
        this.requestFocusInWindow();
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
