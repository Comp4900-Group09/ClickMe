/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author juven1996
 */
public class Circle implements Objects{
    protected Point origin;
    
    public Circle(){
        this(0,0);
    }
    
    public Circle(int x, int y){
        this.origin = new Point(x,y);
    }
    
    public Circle(Point origin){
        this.origin = origin;
    }
    @Override
    public Point getOrigin() {
        return this.origin;
    }

    @Override
    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    @Override
    public void paintComponent(Graphics g) {
        int diameter = (int) (0.5f * 2);
        int x = (int) (origin.x - 0.5f);
        int y = (int) (origin.y - 0.5f);

        g.setColor(Color.BLACK);
        g.fillOval(x, y, diameter, diameter);
    }
    
}
