package socketclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import objectslibrary.SocketObjectWrapper;
import objectslibrary.User;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketClient
{

    private static final String HOST = "145.24.222.149";
    private final static int PORT = 32000;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        User user = new User("username", "password", "email");
        SocketObjectWrapper sow = new SocketObjectWrapper(user, 1);

        SocketClient sc = new SocketClient();

        Object i = sc.communicateWithSocket(sow, PORT);
        System.out.println(i);
    }

    public Object communicateWithSocket(Object obj, int port) throws IOException, ClassNotFoundException
    {
        Socket clientSocket = new Socket(HOST, PORT);

        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

        outToServer.writeObject(obj);

        if (inFromServer.readObject() != null)
        {
            Object fromServer = (Object) inFromServer.readObject();
            if (fromServer != null)
            {
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

        return null;
    }
}
