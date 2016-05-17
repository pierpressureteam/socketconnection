package socketconnectionserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import ships.Ship;

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

                /* Read the object the client has sent from the inputstream */
                Ship inMsg = (Ship) inFromClient.readObject();
                System.out.println("Object as it was received: \n");
                System.out.println(inMsg.getMMSI());
                System.out.println(inMsg.getType());

                /* Edits the object a little bit */
                inMsg.setMMSI(inMsg.getMMSI() + 5000);
                inMsg.setType("Groter");

                System.out.println("Object as it was sent: \n");
                System.out.println(inMsg.getMMSI());
                System.out.println(inMsg.getType());

                /* Send the modified object back */
                outToClient.writeObject(inMsg);

            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
