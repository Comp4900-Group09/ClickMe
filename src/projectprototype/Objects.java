/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author juven1996
 */
public interface Objects {
    Point getOrigin();
    void setOrigin(Point origin);
    void paintComponent(Graphics g);
}
