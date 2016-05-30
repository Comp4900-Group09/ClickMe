package projectprototype;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.embed.swing.JFXPanel;
import javax.swing.JPanel;

public class Menu extends JPanel implements KeyListener {

    public GamePanel panel;
    private final static JFXPanel JFX = new JFXPanel();
    protected static Audio audio = new Audio("bgmusic.mp3");

    public Menu(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
