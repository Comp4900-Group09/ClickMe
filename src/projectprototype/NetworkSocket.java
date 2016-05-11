package projectprototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class NetworkSocket {

    public enum Type {
        CLIENT, SERVER
    }

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

    private boolean isConnected;

    public Type type;

    public NetworkSocket(String type, int port, GamePanel panel, String address) {
        if (type.equals("server")) {
            this.type = Type.SERVER;
            openSocket(port);
        } else if (type.equals("client")) {
            this.type = Type.CLIENT;
            openSocket(port, address);
        }
        createPlayer();
    }

    /*Server openSocket method*/
    public void openSocket(int port) {
        try {
            serverSocket = new ServerSocket(port);
            socket = serverSocket.accept(); //wait for client
        } catch (Exception e) {
            System.err.println("Failed to open socket on port: " + port);
        }
    }

    public void openSocket(int port, String address) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("Failed to open socket on port: " + port);
        }
    }

    public void createPlayer() {
        String t = null;
        if (type == Type.SERVER) {
            t = JOptionPane.showInputDialog("Please enter server player name:");
        } else if (type == Type.CLIENT) {
            t = JOptionPane.showInputDialog("Please enter client player name:");
        }
        panel.player1 = new Player(t);
    }

    public void createObjects() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream()); //open object stream to send circles
            output.flush(); //flush it
            input = new ObjectInputStream(socket.getInputStream()); //open input stream to receive circles
            writer = new PrintWriter(socket.getOutputStream(), true); //open writer for chat
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //open reader for chat
        } catch (Exception e) {
            System.err.println("Failed to create object.");
        }
    }

    public void getPlayer() {
        try {
            panel.player2 = (Player) input.readObject(); //read in player from client
            if (panel.player2 != null) {
                panel.game.player2Label.setText(panel.player2.name);
                send(panel.player1); //send our player
                chat(); //open chat
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chat() {
        isConnected = true;
        Runnable chat = () -> {
            while (isConnected) {
                String msg = null;
                try {
                    msg = reader.readLine();
                } catch (Exception e) {
                    System.err.println("Failed to read.");
                }
                if (msg != null) {
                    panel.game.chat.append(msg + "\n");
                } else if (msg == null) {
                    isConnected = false;
                    panel.game.player2Label.setText("");
                    try {
                        disconnect();
                    } catch (Exception e) {
                        System.err.println("Failed to disconnect.");
                    }
                    //if(type == type.SERVER)
                        //waitForClient();
                }
            }
        };
        chatThread = new Thread(chat);
        chatThread.start();
    }

    public void disconnect() throws IOException {
        serverSocket.close();
        socket.close();
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
}
