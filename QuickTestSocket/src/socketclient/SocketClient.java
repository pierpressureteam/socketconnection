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
    private final static int PORT = 32005;

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        User user = new User("username111", "password", "email");
        SocketObjectWrapper sow = new SocketObjectWrapper(user, 2);

        SocketClient sc = new SocketClient();

        Object i = sc.communicateWithSocket(sow);
        System.out.println(i);
    }

    public Object communicateWithSocket(SocketObjectWrapper sow) throws IOException, ClassNotFoundException
    {
        Socket clientSocket = new Socket(HOST, PORT);

        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

        out.writeObject(sow);
        out.flush();
        Object received = null;

        try
        {
            received = in.readObject();
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }


        out.close();
        in.close();
        clientSocket.close();
        
        return received;

    }
}
