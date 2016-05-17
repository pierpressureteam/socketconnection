package quicktestsocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import ships.Ship;

/**
 *
 * @author Roy van den Heuvel
 */
public class QuickTestSocket {

    static final String HOST = "127.0.0.1";
    static final int PORT = 4444;

    public static void main(String[] args) {
        testSocket();
    }

    public static void testSocket() {
        try {
            // Create the socket
            Socket clientSocket = new Socket(HOST, PORT);
            // Create the input & output streams to the server
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            /* Create object to send */
            Ship msg = new Ship(10000, "Groot");
            
            System.out.println("Object as it was sent: \n");
            System.out.println(msg.getMMSI());
            System.out.println(msg.getType());

            /* Send the object to the server */
            outToServer.writeObject(msg);

            /* Retrieve the object from server */
            Ship msgFrmServer = (Ship) inFromServer.readObject();
            
            System.out.println("Object as it was received again: \n");
            System.out.println(msgFrmServer.getMMSI());
            System.out.println(msgFrmServer.getType());
            
            outToServer.flush();
            outToServer.close();
            inFromServer.close();
            clientSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
