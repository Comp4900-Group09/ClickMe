package projectprototype;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.Timer;

public class Circle implements Serializable {
    
    private static final long serialVersionUID = 5950169519310163575L;
    
    /*Center point of the circle*/
    protected Point origin;
    
    /*Timer to determine when circle should disappear*/
    protected Timer timer;
    protected Timer fade;
    
    /*Player reference to access shape list*/
    protected Player player;
    
    protected Color color;
    protected int difference;
    
    /*Circle constructor.
      @param x: x center point of circle.
      @param y: y center point of circle.
      @param size: size of the circle. Default is 30.
      @param color: color the the circle.
      @param panel: reference to GamePanel.
    */
    public Circle(int x, int y, Color color, int size, Player player) {
        this.origin = new Point(x,y);
        this.player = player;
        this.color = color;
        this.difference = (int)((255.0-0)/(player.CIRCLETIME/100.0));
        timer = new Timer(player.CIRCLETIME, (ActionEvent evt) -> {
            player.objects.remove(Circle.this);
            player.hp--;
            timer.stop();
            fade.stop();
        });
        
        fade = new Timer(player.CIRCLETIME/(player.CIRCLETIME/100), (ActionEvent evt) -> {
            int r = this.color.getRed();
            int g = this.color.getGreen();
            int b = this.color.getBlue();
            r = r + difference < 255 ? r += difference : 255;
            g = g + difference < 255 ? g += difference : 255;
            b = b + difference < 255 ? b += difference : 255;
            this.color = new Color(r, g, b);
        });
        
        if(Debug.doTime) {
            timer.start();
            fade.start();
        }
    }
    
    public Circle(Circle circle, Color color) {
        int width = Game.Width/2;
        this.color = color;
        this.difference = (int)((255.0-0)/(player.CIRCLETIME/100.0));
        width = circle.origin.x-width;
        circle.origin.x = circle.origin.x-(width*2);
        this.origin = circle.origin;
        timer = new Timer(player.CIRCLETIME, (ActionEvent evt) -> {
            player.objects.remove(Circle.this);
            player.hp--;
            timer.stop();
        });
        
        fade = new Timer(player.CIRCLETIME/35, (ActionEvent evt) -> {
            int r = this.color.getRed();
            int g = this.color.getGreen();
            int b = this.color.getBlue();
            r = r + difference < 255 ? r += difference : 255;
            g = g + difference < 255 ? g += difference : 255;
            b = b + difference < 255 ? b += difference : 255;
            this.color = new Color(r, g, b);
        });
        
        if(Debug.doTime) {
            timer.start();
            fade.start();
        }
    }

    /*Unused.*/
    public Circle(Point origin){
        this.origin = origin;
    }
    
    /*Detects whether the user clicked inside a circle or not.
      @param x: x point user clicked on.
      @param y: y point user clicked on.
    */
    public boolean contains(int x, int y) {
        int distance = (x - origin.x) * (x - origin.x) + (y - origin.y) * (y - origin.y);
            if(distance < (player.size*player.size)/4) {
                timer.stop();
                return true;
            }
        return false;
    }
    
    public void clearTimer(){
        this.timer.stop();
    }
    
    @Override
    public boolean equals(Object object) {
        if((Circle)object == this)
            return true;
        Circle compare = (Circle)object;
        int q = Game.Width/2;
        int distance = (compare.origin.x-q)*2;
        if(this.origin.x+distance == compare.origin.x)
            return true;
        else
            return false;
    }
}
