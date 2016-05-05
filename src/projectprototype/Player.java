package projectprototype;

import java.util.ArrayList;

public class Player {
    
    /*Player's HP*/
    protected int hp = 5;
    
    /*Player's name*/
    protected String name;
    
    /*List ob objects the player has placed*/
    protected ArrayList<Circle> objects = new ArrayList<>();
    
    public Player(String name) {
        this.name = name;
    }
}
