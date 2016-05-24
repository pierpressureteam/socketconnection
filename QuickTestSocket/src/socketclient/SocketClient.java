package socketclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import objectslibrary.User;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketClient {

    private static final String HOST = "oveldman.ddns.net";
    private static int PORT = 32000;

    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SocketClient sc = new SocketClient();
        ArrayList al = new ArrayList();
        al.add("1");
        User user = new User();
        user.setPassword("test");
        user.setUsername("test");
        al.add(user);
        int i = (Integer) SocketClient.communicateWithSocket(al, PORT);
        System.out.println(i);
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
