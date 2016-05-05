/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectprototype;

import java.io.*;
import java.net.*;

/**
 *
 * @author juven1996
 */
public class SocketTest {

    static int port = 4242;
    static String host = "127.0.0.2";

    public static void testAvailablility() throws IOException {
        Socket gp3Client = new Socket(host, port);
        OutputStream dataFeed = gp3Client.getOutputStream();
        OutputStreamWriter dataWrite = new OutputStreamWriter(dataFeed);
        try {
            dataWrite.write("<SET ID=\"ENABLE_SEND_TIME\" STATE=\"1\" />\r\n");
            dataWrite.write("<SET ID=\"ENABLE_SEND_POG_FIX\" STATE=\"1\" />\r\n");
            dataWrite.write("<SET ID=\"ENABLE_SEND_CURSOR\" STATE=\"1\" />\r\n");
            dataWrite.write("<SET ID=\"ENABLE_SEND_DATA\" STATE=\"1\" />\r\n");
            System.out.println("TCP client created");
            dataWrite.flush();
        } catch (Exception e) {
            System.out.println("Failed to create TCP client");
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
            System.out.println("Failed to create UDP Client");
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        testAvailablility();
    }
}
