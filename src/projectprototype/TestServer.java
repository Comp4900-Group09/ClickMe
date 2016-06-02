package projectprototype;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

    static Player player1;
    static Player player2;
    static ObjectInputStream inputP1;
    static ObjectOutputStream outputP1;
    static PrintWriter writerP1;
    static BufferedReader readerP1;

    static ObjectInputStream inputP2;
    static ObjectOutputStream outputP2;
    static PrintWriter writerP2;
    static BufferedReader readerP2;

    static ServerSocket serverSocketP1;
    static ServerSocket serverSocketP2;
    static Socket socketP1;
    static Socket socketP2;
    static Socket gameSocketP1;
    static Socket gameSocketP2;
    static ServerSocket serverGameSocketP1;
    static ServerSocket serverGameSocketP2;

    public static void main(String[] args) {
        try {
            serverSocketP1 = new ServerSocket(9001); //open socket
            serverGameSocketP1 = new ServerSocket(9002); //open socket
            socketP1 = serverSocketP1.accept(); //wait for client
            gameSocketP1 = serverGameSocketP1.accept();
            outputP1 = new ObjectOutputStream(gameSocketP1.getOutputStream()); //open object stream to send circles
            outputP1.flush(); //flush it
            inputP1 = new ObjectInputStream(gameSocketP1.getInputStream()); //open input stream to receive circles
            writerP1 = new PrintWriter(socketP1.getOutputStream(), true); //open writer for chat
            readerP1 = new BufferedReader(new InputStreamReader(socketP1.getInputStream())); //open reader for chat
            //client 1 connected
            System.out.println("Connected");
            serverSocketP2 = new ServerSocket(9003); //open socket
            serverGameSocketP2 = new ServerSocket(9004); //open socket
            socketP2 = serverSocketP2.accept(); //wait for client
            gameSocketP2 = serverGameSocketP2.accept();
            //client 2 connected
            outputP2 = new ObjectOutputStream(gameSocketP2.getOutputStream()); //open object stream to send circles
            outputP2.flush(); //flush it
            inputP2 = new ObjectInputStream(gameSocketP2.getInputStream()); //open input stream to receive circles
            writerP2 = new PrintWriter(socketP2.getOutputStream(), true); //open writer for chat
            readerP2 = new BufferedReader(new InputStreamReader(socketP2.getInputStream())); //open reader for chat
            System.out.println("Connected");

            receivePlayer2();
            receivePlayer1();

            receivePlayer2Message();
            receivePlayer1Message();

            while (true) {

            }

        } catch (Exception e) {
            System.err.println("Failed");
        }
    }

    /*Player 2 sends player information through this method.*/
    public static void receivePlayer2() {
        try {
            player2 = (Player) inputP2.readObject(); //read in player2 and send to host
            if (player2 != null) {
                outputP1.writeObject(player2);
                //lobby.player2Label.setText(panel.player2.name);
                //send(panel.player1); //send our player
                //chat(); //open chat
            }
        } catch (Exception e) {
            System.err.println("Broke");
        }
    }

    /*Player 2 sends player information through this method.*/
    public static void receivePlayer1() {
        try {
            player1 = (Player) inputP1.readObject(); //read in player2 and send to host
            if (player1 != null) {
                outputP2.writeObject(player1);
                //lobby.player2Label.setText(panel.player2.name);
                //send(panel.player1); //send our player
                //chat(); //open chat
            }
        } catch (Exception e) {
            System.err.println("Broke");
        }
    }

    public static void receivePlayer2Message() {
        Runnable chat = () -> {
            while (true) { //fix this
                String msg = null;
                try {
                    msg = readerP2.readLine();
                } catch (Exception e) {
                    System.err.println("Failed to read.");
                    e.printStackTrace();
                }
                if (msg != null) {
                    writerP1.write(msg + '\n');
                    writerP1.flush();
                }
                /*else if (msg == null) {
                    clientConnected = false;
                    lobby.ready2.setSelected(false);
                    lobby.player2Label.setText("");
                    try {
                        disconnect();
                    } catch (Exception e) {
                        System.err.println("Failed to disconnect.");
                    }
                    //waitForClient();
                }*/
            }
        };
        Thread chatThread = new Thread(chat);
        chatThread.start();
    }

    public static void receivePlayer1Message() {
        Runnable chat = () -> {
            while (true) { //fix this
                String msg = null;
                try {
                    msg = readerP1.readLine();
                } catch (Exception e) {
                    System.err.println("Failed to read.");
                    e.printStackTrace();
                }
                if (msg.equals(player1.name + ": " + "start")) {
                    gamePlayer1();
                    gamePlayer2();
                }
                if (msg != null) {
                    writerP2.write(msg + '\n');
                    writerP2.flush();
                }
                /*else if (msg == null) {
                    clientConnected = false;
                    lobby.ready2.setSelected(false);
                    lobby.player2Label.setText("");
                    try {
                        disconnect();
                    } catch (Exception e) {
                        System.err.println("Failed to disconnect.");
                    }
                    //waitForClient();
                }*/
            }
        };
        Thread chatThread2 = new Thread(chat);
        chatThread2.start();
    }

    public static void gamePlayer1() {
        Runnable game = () -> {
            while (true) {
                Circle circle = null;
                try {
                    circle = (Circle) inputP1.readObject();
                    outputP2.writeObject(circle);
                } catch (Exception e) {
                    System.err.println("Failed");
                }
            }
        };
        Thread gameplay = new Thread(game);
        gameplay.start();
    }

    public static void gamePlayer2() {
        Runnable game = () -> {
            while (true) {
                Circle circle = null;
                try {
                    circle = (Circle) inputP2.readObject();
                    outputP1.writeObject(circle);
                } catch (Exception e) {
                    System.err.println("Failed");
                }
            }
        };
        Thread gameplay = new Thread(game);
        gameplay.start();
    }
}
