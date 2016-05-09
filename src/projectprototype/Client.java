package projectprototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    /*Used to send circles over socket*/
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    /*Used to read chat over sockets*/
    private PrintWriter inputWriter;
    private BufferedReader outputReader;

    public Client() {
        try {
            Socket socket = new Socket("localhost", 4444);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch(Exception e) {}
        startListening();
    }

    public Client(String address) {
        try {
            Socket socket = new Socket(address, 4444);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {}
        startListening();
    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }
    
    public void startListening() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Circle circle = null;
                    try {
                        circle = (Circle)input.readObject();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    if(circle != null) {
                        circle = new Circle(circle);
                        circle.player = GamePanel.player1;
                        GamePanel.player1.objects.add(circle);
                    }
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
}
