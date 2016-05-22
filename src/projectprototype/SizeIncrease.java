package projectprototype;

import java.awt.Color;

public class SizeIncrease extends Powerup {
    
    public SizeIncrease(int x, int y, GamePanel panel) {
        super(x, y, panel);
        this.color = Color.green;
    }
    
    public void apply() {
        panel.player1.size = 60;
    }
}
