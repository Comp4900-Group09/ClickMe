package projectprototype;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Circle {
    
    /*Center point of the circle*/
    protected Point origin;
    
    /*Circle color*/
    protected Color color;
    
    /*Timer to determine when circle should disappear*/
    protected Timer timer;
    
    /*Game panel to access shape list*/
    protected GamePanel panel;
    
    /*Time the circle should stay on screen. Default is 3000 (3 seconds)*/
    protected final int CIRCLETIME = 3000;
    
    /*Size of the circle. Default is 30*/
    protected int size;
    
    /*Circle constructor.
      @param x: x center point of circle.
      @param y: y center point of circle.
      @param size: size of the circle. Default is 30.
      @param color: color the the circle.
      @param panel: reference to GamePanel.
    */
    public Circle(int x, int y, int size, Color color, GamePanel panel) {
        this.origin = new Point(x,y);
        this.size = size;
        this.color = color;
        this.panel = panel;
        timer = new Timer(CIRCLETIME, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                panel.objectList.remove(Circle.this);
                panel.clear();
                panel.player2.hp--;
                timer.stop();
            }    
        });
        timer.start();
    }

    /*Unused.*/
    public Circle(Point origin){
        this.origin = origin;
    }

    /*Sets color of circle.
      @param color: new color of circle.
    */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /*Detects whether the user clicked inside a circle or not.
      @param x: x point user clicked on.
      @param y: y point user clicked on.
    */
    public boolean contains(int x, int y) {
        int distance = (x - origin.x) * (x - origin.x) + (y - origin.y) * (y - origin.y);
            if(distance < (size*size)/4) {
                timer.stop();
                return true;
            }
        return false;
    }
}
