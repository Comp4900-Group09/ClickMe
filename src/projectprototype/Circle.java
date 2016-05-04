/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author juven1996
 */
public class Circle {
    
    protected Point origin;
    protected Color color;
    protected Timer timer;
    protected GamePanel panel;
    protected final int CIRCLETIME = 3000;
    
    public Circle(int x, int y, Color color, GamePanel panel) {
        this.origin = new Point(x,y);
        this.color = color;
        this.panel = panel;
        timer = new Timer(CIRCLETIME, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                panel.objectList.remove(Circle.this);
                panel.clear();
                timer.stop();
            }    
        });
        timer.start();
    }

    public Circle(Point origin){
        this.origin = origin;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
