package projectprototype;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

    /*Used to send circles over socket*/
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    private Thread chatThread;

    private Socket socket;

    /*Used to read chat over sockets*/
    private PrintWriter writer;
    private BufferedReader reader;

    protected GamePanel panel;
    protected Lobby lobby;

    public boolean isConnected = false;
    public boolean playing = false;

    public Client(String address, GamePanel panel) {
        this.panel = panel;
        createPlayer();
        if(openSocket(address)) { //if socket was connected
            isConnected = true;
            try {
                send(panel.player1);
                panel.player2 = (Player) input.readObject();
            } catch (Exception e) {
                System.err.println("Failed to read or send player.");
            }
            isConnected = true;
            this.lobby = new Lobby(panel.player2, panel.player1, panel, true);
            panel.game.showMenu(lobby);
            chat();
        }
    }

    public boolean openSocket(String address) {
        try {
            socket = new Socket(address, 4444);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();
            input = new ObjectInputStream(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            System.out.println("No connection.");
            return false;
        }
        return true;
    }

    public void chat() {
        Runnable chat = () -> {
            while (isConnected && !playing) {
                String msg = null;
                try {
                    msg = reader.readLine();
                } catch (Exception e) {
                    isConnected = false;
                }
                if (msg != null) {
                    parseMessage(msg);
                }
            }
        };
        chatThread = new Thread(chat);
        chatThread.start();
    }
    
    public void parseMessage(String msg) {
        if(msg.equals(panel.player2.name + ": " + "ready"))
            lobby.ready1.setSelected(!lobby.ready1.isSelected());
        else if(msg.equals(panel.player2.name + ": " + "start")) {
            this.panel.newGame(panel.player1, panel.player2);
            this.panel.timer.start();
            panel.game.showMenu(panel);
            startListening();
        }
        else
            lobby.chat.append(msg + "\n");
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void createPlayer() {
        String t = JOptionPane.showInputDialog("Please enter client player name:");
        panel.player1 = new Player(t);
    }

    public void send(Circle circle) throws IOException {
        output.writeObject(circle);
    }

    public void send(Player player) throws IOException {
        output.writeObject(player);
    }

    public void send(String msg) {
        if(msg.equals("ready"))
            lobby.ready2.setSelected(!lobby.ready2.isSelected());
        writer.write(panel.player1.name + ": " + msg + "\n");
        writer.flush();
    }

    public void startListening() {
        playing = true;
        Runnable serverTask = () -> {
            while (true) {
                Circle circle = null;
                try {
                    circle = (Circle)input.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (circle != null) {
                    circle = new Circle(circle);
                    circle.player = panel.player1;
                    panel.player1.objects.add(circle);
                }
                else if(circle == null) { //host disconnects
                    panel.timer.stop();
                    panel.game.showMenu(new MainMenu(panel));
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }
}
