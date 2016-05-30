package projectprototype;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    
    /*Player's HP*/
    protected int hp = 5;
    
    /*Player's name*/
    protected String name;
    
    /*List of objects on the players field*/
    protected ArrayList<Circle> objects = new ArrayList<>();
    
    /*Size of the circle. Default is 30*/
    protected int size = 60;
    protected int CIRCLETIME = 3000;
    
    public Player(String name) {
        this.name = name;
    }
}
