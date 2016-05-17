/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketconnectionserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketConnectionServer {

    static final int PORT = 4444;

    public static void main(String[] args) {

        startServer();

    }

    public static void startServer() {
        try {

            ServerSocket welcomeSocket = new ServerSocket(PORT);
            System.out.println("Socket created.");

            while (true) {
                // Create the Client Socket
                Socket clientSocket = welcomeSocket.accept();
                System.out.println("Socket accepted.");

                // Create input and output streams to client
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

                Ship inMsg = (Ship) inFromClient.readObject();
                System.out.println(inMsg.MMSI);
                System.out.println(inMsg.type);

                inMsg.setMMSI(inMsg.getMMSI() + 5000);
                inMsg.setType("Groter");


                /* Send the modified Message object back */
                outToClient.writeObject(inMsg);

            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("To String: " + e.toString());
            e.printStackTrace();
        }
    }
}
