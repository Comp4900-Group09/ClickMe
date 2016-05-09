package projectprototype;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Serializable {
    
    /*Used to send circles over socket*/
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    /*Used to read chat over sockets*/
    private PrintWriter inputWriter;
    private BufferedReader outputReader;
    
    private GamePanel panel;
    
    protected boolean clientConnected = false;

    public Server(GamePanel panel) {
        this.panel = panel;
        startServer();
    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }

    public void startServer() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(4444);
                    Socket socket = serverSocket.accept();
                    clientConnected = socket.isConnected();
                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(socket.getInputStream());
                } catch (Exception e) {
                }
                while (true) {
                    Circle circle = null;
                    try {
                        circle = (Circle) input.readObject();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (circle != null) {
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
