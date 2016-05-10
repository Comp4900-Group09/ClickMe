package projectprototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server implements Serializable {

    /*Used to send circles over socket*/
    private ObjectInputStream input;
    private ObjectOutputStream output;

    /*Used to read chat over sockets*/
    private PrintWriter writer;
    private BufferedReader reader;

    private GamePanel panel;

    public Server(GamePanel panel) {
        this.panel = panel;
        String t = JOptionPane.showInputDialog("Please enter server player name:");
        panel.player1 = new Player(t);
        waitForClient();
        //startServer();
    }

    public void chat() {
        Runnable chat = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String msg = null;
                    try {
                        msg = reader.readLine();
                    } catch (Exception e) {
                        System.err.println("Failed to read message.");
                    }
                    if (msg != null) {
                        panel.game.chat.append(msg + "\n");
                        msg = null;
                    }
                }
            }
        };
        Thread chatThread = new Thread(chat);
        chatThread.start();
    }

    public void waitForClient() {
        Runnable client = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(4444);
                    Socket socket = serverSocket.accept();
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(socket.getInputStream());
                    try {
                        panel.player2 = (Player) input.readObject();
                        if (panel.player2 != null) {
                            panel.game.player2Label.setText(panel.player2.name);
                            send(panel.player1);
                            chat();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                }
            }
        };
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }

    public void send(Player player) throws IOException {
        output.writeObject(player);
    }

    public void send(String msg) {
        writer.write(msg);
        writer.flush();
    }

    public void startServer() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Circle circle = null;
                    try {
                        circle = (Circle) input.readObject();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (circle != null) {
                        circle = new Circle(circle);
                        //circle.player = GamePanel.player1;
                        //GamePanel.player1.objects.add(circle);
                    }
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
}
