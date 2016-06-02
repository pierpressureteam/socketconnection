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
import java.util.ArrayList;
import objectslibrary.Ship;
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
    private static final int PORT = 32007;

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
            received = (SocketObjectWrapper) in.readObject();
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
                // Get data of a ship based on MMSI
                Ship ship = (Ship) sow.getSocketObject();
                return getShipEmissionLocationData(ship.getMMSI());
            }
            if (method == 4)
            {
                // Get speed of a ship based on MMSI
                Ship ship = (Ship) sow.getSocketObject();
                return getSpeedData(ship.getMMSI());
            }
        }
        return null;
    }

    public ArrayList<Ship> getSpeedData(int MMSI) throws SQLException
    {
        Connection conn = DriverManager.getConnection(myUrl, username, password);

        PreparedStatement ps = conn.prepareStatement("SELECT ships_mmsi,speed,current_time_ais from aisinformation WHERE ships_mmsi = ?;");

        ps.setInt(1, MMSI);

        ResultSet rs = ps.executeQuery();
        ArrayList<Ship> shipList = new ArrayList();

        while (rs.next())
        {
            int mmsi = rs.getInt(1);
            double speed = rs.getDouble(2);
            long time = rs.getLong(3);

            Ship ship = new Ship(mmsi, time, speed);

            shipList.add(ship);
        }

        return shipList;
    }

    public ArrayList<Ship> getShipEmissionLocationData(int MMSI) throws SQLException
    {

        Connection conn = DriverManager.getConnection(myUrl, username, password);

        PreparedStatement ps = conn.prepareStatement("select ships_mmsi,current_time_ais,x_coordinates,y_coordinates,co2_submission from aisinformation WHERE ships_mmsi = ?;");

        ps.setInt(1, MMSI);
        ArrayList<Ship> shipList = new ArrayList();
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            int mmsi = rs.getInt(1);
            long time = rs.getLong(2);
            double x = rs.getDouble(3);
            double y = rs.getDouble(4);
            double emission = rs.getDouble(5);
            Ship ship = new Ship(mmsi, x, y, time, emission);

            shipList.add(ship);
        }

        return shipList;

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
        if (ps.execute() == false || true)
        {
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
