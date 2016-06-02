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
    private final static int PORT = 32001;
    private final static int PORT2 = 32002;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        User user = new User("username", "password", "email");
        SocketObjectWrapper sow = new SocketObjectWrapper(user, 1);

        SocketClient sc = new SocketClient();

        Object i = sc.communicateWithSocket(sow);
        System.out.println(i);
    }

    public Object communicateWithSocket(SocketObjectWrapper obj) throws IOException, ClassNotFoundException
    {
        Socket clientSocketIn = new Socket(HOST, PORT);
        Socket clientSocketOut = new Socket(HOST, PORT2);

        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocketOut.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocketIn.getInputStream());

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
