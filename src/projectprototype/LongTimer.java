package projectprototype;

import java.awt.Color;

public class LongTimer extends Powerup {
    
    public LongTimer(int x, int y, GamePanel panel) {
        super(x, y, panel);
        this.color = Color.blue;
    }
    
    public void apply() {
        panel.player1.CIRCLETIME = 6000;
    }
}
