package socketserver;

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
public class SocketServer {

    static final int PORT = 4444;

    public static void main(String[] args) {
        startServer();
    }
    

    public static void startServer() {
        try {
            ServerSocket welcomeSocket = new ServerSocket(PORT);

            while (true) {
                // Create the Client Socket
                Socket clientSocket = welcomeSocket.accept();

                // Create input and output streams to client
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

                Object checkedObject = checkObject(inFromClient);
                
                

                /* Send the modified object back */
                outToClient.writeObject(checkedObject);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Object checkObject(Object obj){
        if(obj != null){
            int intFrmObj = (Integer) obj;
            
            if(intFrmObj == 1){
                
            }
            if(intFrmObj == 2){
                
            }
            if(intFrmObj == 3){
                
            }
        }
        return null;
    }
}
