package socketserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static socketserver.DatabaseConnection.myUrl;
import static socketserver.DatabaseConnection.password;
import static socketserver.DatabaseConnection.username;
import objectslibrary.User;
import objectslibrary.SocketObjectWrapper;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketServer
{

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final int PORT = 32005;

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException
    {
        Class.forName(JDBC_DRIVER);
        System.out.println("PORT IN USE: " + PORT);
        SocketServer ss = new SocketServer();
        ss.startDataServer(PORT);
    }

    public SocketServer()
    {

    }

    /**
     *
     * @param port
     * @throws IOException
     * @throws SQLException
     */
    public void startDataServer(int port) throws IOException, SQLException
    {
        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(port);
        } catch (IOException e)
        {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }

        Socket clientSocket = null;

        try
        {
            System.out.println("Waiting for Client");
            clientSocket = serverSocket.accept();
        } catch (IOException e)
        {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        ObjectOutputStream out = new ObjectOutputStream(
                clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(
                clientSocket.getInputStream());


        Object checkedObject = null;
        SocketObjectWrapper received = null;
        try
        {
            received =  (SocketObjectWrapper) in.readObject();
            checkedObject = checkObject(received);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        System.out.println("Server recieved object: " + received + " from Client");

        
        System.out.println("Server sending point: " + checkedObject + " to Client");
        out.writeObject(checkedObject);
        out.flush();

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();

        startDataServer(port);
        
    }

    /**
     *
     *
     *
     * @param sow
     * @return Object
     * @throws SQLException
     */
    public Object checkObject(SocketObjectWrapper sow) throws SQLException
    {
        if (sow != null)
        {
            int method = sow.getMethodToCall();

            if (method == 1)
            {
                //User validation
                User userToValidate = (User) sow.getSocketObject();
                return validateUser(userToValidate);
            }
            if (method == 2)
            {
                // User registration
                User userToRegister = (User) sow.getSocketObject();
                return registerUser(userToRegister);
            }
            if (method == 3)
            {
                return method;
            }
        }
        return null;
    }

    /**
     *
     * @param user to register, has to be of type User.
     * @return a boolean depending on whether the method succeeded or not.
     * @throws SQLException
     *
     */
    public boolean registerUser(User user) throws SQLException
    {
        Connection conn = DriverManager.getConnection(myUrl, username, password);

        PreparedStatement ps = conn.prepareStatement("INSERT INTO usersaccount(username, password, email) VALUES(?, ?, ?)");

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());

        // If the query is executed it will return false or true depending on if it is an insert query or not.
        // This means that to ensure we return true when the method succeeds we have to check whether it is either false or true.
        if(ps.execute() == false || true){
            return true;
        }
        // if this return is reached, this means the query did not get executed properly. The method will have returned null.
        return false;
        
    }

    /**
     *
     * @param user to validate, has to be of type User.
     * @return a boolean depending on whether the method succeeded or not.
     * @throws SQLException
     */
    public boolean validateUser(User user) throws SQLException
    {
        Connection conn = DriverManager.getConnection(myUrl, username, password);

        PreparedStatement ps = conn.prepareStatement("SELECT u.username FROM usersaccount u WHERE username = ? AND password = ?");

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());

        
        ResultSet rs = ps.executeQuery();
        
        return rs.first();
    }
}
