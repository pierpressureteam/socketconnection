package socketclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketClient {

    private static final String HOST = "127.0.0.1";
    private static int PORT = 4444;

    public SocketClient() {

    }

    public static Object communicateWithSocket(Object obj, int port) throws IOException, ClassNotFoundException {
        Socket clientSocket = new Socket(HOST, PORT);

        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

        outToServer.writeObject(obj);

        Object fromServer = (Object) inFromServer.readObject();
        if (fromServer != null) {
            outToServer.flush();
            outToServer.close();
            inFromServer.close();
            return fromServer;
        }

        outToServer.flush();
        outToServer.close();
        inFromServer.close();
        return null;

    }
}
