package projectprototype;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server implements Serializable {

    private ObjectInputStream input;
    private ObjectOutputStream output;

    private GamePanel panel;

    protected Player serverPlayer;
    protected Player clientPlayer;
    protected boolean clientConnected = false;

    public Server(GamePanel panel) {
        this.panel = panel;
        startServer();

    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }

    public void send(Player player) throws IOException {
        output.writeObject(player);
    }

    public void startServer() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                String t = JOptionPane.showInputDialog("Please enter server player name:");
                serverPlayer = new Player(t);
                try {
                    ServerSocket serverSocket = new ServerSocket(4444);
                    Socket socket = serverSocket.accept();
                    clientConnected = socket.isConnected();
                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(socket.getInputStream());
                    try {
                        clientPlayer = (Player) input.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
