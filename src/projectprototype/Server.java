package projectprototype;
import java.awt.event.ActionEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.Timer;

public class Server implements Serializable {
    
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    private GamePanel panel;
    
    public Server(GamePanel panel) {
        this.panel = panel;
        try {
            ServerSocket serverSocket = new ServerSocket(4444);
            Socket socket = serverSocket.accept();
            System.out.println("Connected");
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
        } catch(Exception e) {}
        System.out.println("Out");
        startServer();
    }
    
    public void startServer() {
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
                        circle.player = GamePanel.player2;
                        GamePanel.player2.objects.add(circle);
                    }
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
}
