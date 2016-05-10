package projectprototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

    /*Used to send circles over socket*/
    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    
    /*Used to read chat over sockets*/
    private PrintWriter inputWriter;
    private BufferedReader outputReader;

    protected Player clientPlayer;
    protected Player serverPlayer;

    public Client(String address) {
        String t = JOptionPane.showInputDialog("Please enter client player name:");
        clientPlayer = new Player(t);
        try {
            Socket socket = new Socket(address, 4444);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            System.out.println("Client connected to server.");
        } catch (Exception e) {}
        startListening();
    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }

    public void send(Player player) throws IOException {
        output.writeObject(player);
    }

    public void startListening() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                try {
                    send(clientPlayer);
                    System.out.println("Client sent client player data to server.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    serverPlayer = (Player) input.readObject();
                    System.out.println("Received server player data from server");
                } catch (Exception e) {
                    e.printStackTrace();
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
