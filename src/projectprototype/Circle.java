/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author juven1996
 */
public class Circle {
    protected Point origin;
    protected Color color;
    
    public Circle(int x, int y, Color color) {
        this.origin = new Point(x,y);
        this.color = color;
    }
    
    public Circle(Point origin){
        this.origin = origin;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
}
