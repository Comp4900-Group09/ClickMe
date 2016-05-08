package projectprototype;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    public Client() {
        try {
            Socket socket = new Socket("localhost", 4444);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch(Exception e) {}
    }
    
    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }
}
