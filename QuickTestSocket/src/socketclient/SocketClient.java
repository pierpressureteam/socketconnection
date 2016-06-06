package socketclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;

import objectslibrary.SocketObjectWrapper;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketClient
{

    private final static String HOST = "145.24.222.149";
    private final static String OSCARHOST = "77.163.77.9";
    private final static int PORT = 32007;

    public Object communicateWithSocket(SocketObjectWrapper sow) throws IOException, ClassNotFoundException
    {
        Socket clientSocket = new Socket(OSCARHOST, PORT);

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
