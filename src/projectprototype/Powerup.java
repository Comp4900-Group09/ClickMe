package projectprototype;

import java.awt.Color;
import java.awt.Point;

public class Powerup  {
    
    protected GamePanel panel;
    protected Color color;
    protected Point origin;
    
    public Powerup(int x, int y, GamePanel panel) {
        this.panel = panel;
        this.origin = new Point(x, y);
    }
    
    public boolean contains(int x, int y) {
        int distance = (x - origin.x) * (x - origin.x) + (y - origin.y) * (y - origin.y);
            if(distance <= (panel.player1.size*panel.player1.size)/4) {
                return true;
            }
        return false;
    }
    
    public void apply() {}
    
}
