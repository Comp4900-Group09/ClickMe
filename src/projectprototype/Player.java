package projectprototype;

import java.util.ArrayList;

public class Player {
    
    /*Player's HP*/
    protected int hp = 5;
    
    /*Player's name*/
    protected String name;
    
    /*List of objects the OPPONENT has placed*/
    protected ArrayList<Circle> objects = new ArrayList<>();
    
    /*Size of the circle. Default is 30*/
    protected int size = 30;
    
    public Player(String name) {
        this.name = name;
    }
}
