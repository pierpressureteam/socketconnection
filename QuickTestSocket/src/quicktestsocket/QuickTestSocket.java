/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quicktestsocket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Roy van den Heuvel
 */
public class QuickTestSocket {

    static final String HOST = "127.0.0.1";
    static final int PORT = 4444;

    
    public static void testSocket() {
        try {
            // Create the socket
            Socket clientSocket = new Socket(HOST, PORT);
            // Create the input & output streams to the server
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            /* Create The Message Object to send */
            Ship msg = new Ship(748382, "Groot");

            /* Send the Message Object to the server */
            outToServer.writeObject(msg);

            /* Retrieve the Message Object from server */
            Ship msgFrmServer = null;
            msgFrmServer = (Ship) inFromServer.readObject();

            /* Print out the recived Message */
            System.out.println("MMSI: " + msgFrmServer.MMSI);
            System.out.println("Type: " + msgFrmServer.type);

            outToServer.flush();
            outToServer.close();
            inFromServer.close();
            clientSocket.close();

        } catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + e.getStackTrace());
            e.printStackTrace();
        }
    }
}
