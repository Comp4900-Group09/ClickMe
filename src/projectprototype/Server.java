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
    
    private ServerSocket serverSocket;
    private Socket socket;

    /*Used to read chat over sockets*/
    private PrintWriter writer;
    private BufferedReader reader;

    protected GamePanel panel;
    
    private Thread chatThread;
    private Thread clientThread;
    
    private boolean clientConnected, serverDisconnected = false;

    public Server(GamePanel panel) {
        this.panel = panel;
        String t = JOptionPane.showInputDialog("Please enter server player name:");
        panel.player1 = new Player(t);
        waitForClient();
        //startServer();
    }

    public void chat() {
        clientConnected = true;
        Runnable chat = () -> {
            while (clientConnected) {
                String msg = null;
                try {
                    msg = reader.readLine();
                } catch (Exception e) {
                    System.err.println("Failed to read.");
                }
                if (msg != null) {
                    panel.game.chat.append(msg + "\n");
                }
                else if(msg == null) {
                    clientConnected = false;
                    panel.game.player2Label.setText("");
                    try {
                        disconnect();
                    } catch(Exception e) {
                        System.err.println("Failed to disconnect.");
                    }
                    waitForClient();
                }
            }
        };
        chatThread = new Thread(chat);
        chatThread.start();
    }
    
    public void disconnect() throws IOException {
        send("<CLOSE_SERVER>");
        serverDisconnected = true;
        serverSocket.close();
        socket.close();
    }

    public void waitForClient() {
        Runnable client = () -> {
            try {
                serverSocket = new ServerSocket(4444); //open socket
                socket = serverSocket.accept(); //wait for client
                output = new ObjectOutputStream(socket.getOutputStream()); //open object stream to send circles
                output.flush(); //flush it
                input = new ObjectInputStream(socket.getInputStream()); //open input stream to receive circles
                writer = new PrintWriter(socket.getOutputStream(), true); //open writer for chat
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //open reader for chat
                try {
                    panel.player2 = (Player) input.readObject(); //read in player from client
                    if (panel.player2 != null) {
                        panel.game.player2Label.setText(panel.player2.name);
                        send(panel.player1); //send our player
                        chat(); //open chat
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        clientThread = new Thread(client);
        clientThread.start();
    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }

    public void send(Player player) throws IOException {
        output.writeObject(player);
    }
    
    public void send(Game game) throws IOException {
        output.writeObject(game);
    }

    public void send(String msg) {
        writer.write(panel.player1.name + ": " + msg + "\n");
        writer.flush();
    }

    public void startServer() {
        Runnable serverTask = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Circle circle = null;
                    String msg = null;
                    try {
                        circle = (Circle) input.readObject();
                    } catch (Exception e) {
                        try {
                            msg = (String)input.readObject();
                        } catch(Exception f) {}
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
