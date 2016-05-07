package projectprototype;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class GazePoint {
    
    protected int port = 4242;
    protected String host = "127.0.0.2";
    protected Socket gp3Client = null;
    protected InputStream dataFeed = null;
    protected PrintWriter dataWrite = null;
    
    public GazePoint() {
        createSocket();
        createStreams();
        initialize();
    }
    
    public void createSocket() {
        try {
            gp3Client = new Socket(host, port);
        } catch(Exception e) {
            System.err.println("Could not create socket.");
        }
    }
    
    public void createStreams() {
        try {
            dataFeed = gp3Client.getInputStream();
            dataWrite = new PrintWriter(gp3Client.getOutputStream());
        } catch(Exception e) {
            System.err.println("Could not create streams.");
        }
    }
    
    public void initialize() {
        try {
            dataWrite.write("<SET ID=\"ENABLE_SEND_TIME\" STATE=\"1\" />\r\n");
            dataWrite.write("<SET ID=\"ENABLE_SEND_POG_FIX\" STATE=\"1\" />\r\n");
            dataWrite.write("<SET ID=\"ENABLE_SEND_CURSOR\" STATE=\"1\" />\r\n");
            dataWrite.write("<SET ID=\"ENABLE_SEND_DATA\" STATE=\"1\" />\r\n");
            dataWrite.write("<SET ID=\"SCREEN_SIZE\" X=\"0\" Y=\"0\" WIDTH=\"1280\" HEIGHT=\"1024\"/>");
            System.out.println("TCP client created");
            dataWrite.flush();
        } catch (Exception e) {
            System.err.println("Failed to create TCP client");
        }
        try {
            InetAddress IP = InetAddress.getLocalHost();
            InetSocketAddress groupEP = new InetSocketAddress(IP.getHostAddress(), 4244);
            DatagramSocket udpClient = new DatagramSocket(4244);
            udpClient.setReuseAddress(true);
            dataWrite.write("<SET ID=\"IMAGE_TX\" ADDRESS=\"127.0.0.1\" PORT=\"4244\" />\r\n");
            dataWrite.flush();
            System.out.println("UDP client created");
        } catch (Exception e) {
            System.err.println("Failed to create UDP Client");
        }
    }
    
    public Point getCoordinates() throws IOException {
        String command = "";
        do {
            int read = dataFeed.read();
            command += (char)read;
            if(command.contains("\r\n")) {
                if(command.contains("<REC")) {
                    int start = command.indexOf("FPOGX=\"") + "FPOGX=\"".length();
                    int end = command.indexOf("\"", start);
                    double i = Double.parseDouble(command.substring(start, end));
                    start = command.indexOf("FPOGY=\"") + "FPOGY=\"".length();
                    end = command.indexOf("\"", start);
                    double j = Double.parseDouble(command.substring(start, end));
                    
                    return new Point((int)(Game.Width*i), (int)(Game.Height*j));
                }
                 command = "";
            }
            
        } while(true);
    }
}
