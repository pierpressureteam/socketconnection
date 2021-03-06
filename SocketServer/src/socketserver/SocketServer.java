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
import java.sql.Savepoint;

import java.util.ArrayList;
import java.util.Date;
<<<<<<< HEAD
=======
import objectslibrary.GeneralShipData;

>>>>>>> 35364143e11227216e6b8c1403b265d30f018d54
import objectslibrary.Ship;
import objectslibrary.User;
import objectslibrary.SocketObjectWrapper;

import static socketserver.DatabaseConnection.DBURL;
import static socketserver.DatabaseConnection.PASSWORD;
import static socketserver.DatabaseConnection.USERNAME;

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
        } catch (IOException | ClassNotFoundException | SQLException ex)
        {
            System.out.println(ex.getMessage());
        }

        if (received != null)
        {
            System.out.println("Server recieved object: " + received.toString() + " from client.");

        } else
        {
            System.out.println("Server received object: null from client.");
        }
        if (checkedObject != null)
        {
            System.out.println("Server sending object: " + checkedObject.toString() + " to client.");
        } else
        {
            System.out.println("Server sending object: null to client.");
        }

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
                return getSortedSpeedData(ship.getMMSI());
            }
            if (method == 5)
            {
                // Get all the available ship types from the database.
                return getShipTypesAvailable();
            }
            if (method == 6)
            {
                // Get the very last entry for a ship.
                Ship ship = (Ship) sow.getSocketObject();
                return getLastShipData(ship.getMMSI());
            }
<<<<<<< HEAD
=======
            if (method == 7)
            {
                // Get average of ship category.
                Ship ship = (Ship) sow.getSocketObject();
                return getCategoryAverage(ship);
            }
            System.err.print("Invalid method was called by client.");
            return "Invalid method call.";
>>>>>>> 35364143e11227216e6b8c1403b265d30f018d54
        }
        System.err.print("SocketObjectWrapper received by client was null.");
        return "SocketObjectWrapper received was null";
    }

    public GeneralShipData getCategoryAverage(Ship ship) throws SQLException
    {
        double average = 0;
        double lowest = 0;
        double highest = 0;
        String type = "";

        boolean oneSuccess = false;
        boolean twoSuccess = false;
        boolean threeSuccess = false;

        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        PreparedStatement ps = conn.prepareCall("SELECT AVG(co2_submission) FROM aisinformation, ships, shiptype WHERE ships.mmsi = aisinformation.ships_mmsi AND ships.shiptype_typename = shiptype.typename AND shiptype.typebigname = ?;");
        PreparedStatement lowestPs = conn.prepareCall("SELECT co2_submission FROM aisinformation, ships, shiptype WHERE ships.mmsi = aisinformation.ships_mmsi AND ships.shiptype_typename = shiptype.typename AND shiptype.typebigname = ? ORDER BY co2_submission ASC LIMIT 1;");
        PreparedStatement highestPs = conn.prepareCall("SELECT co2_submission FROM aisinformation, ships, shiptype WHERE ships.mmsi = aisinformation.ships_mmsi AND ships.shiptype_typename = shiptype.typename AND shiptype.typebigname = ? ORDER BY co2_submission DESC LIMIT 1;");
        PreparedStatement shipType = conn.prepareStatement("SELECT typebigname FROM shiptype, ships WHERE ships.mmsi = ? AND ships.shiptype_typename = shiptype.typename;");
        
        shipType.setInt(1, ship.getMMSI());
       

        GeneralShipData gsd = new GeneralShipData();

        ResultSet shipsType = shipType.executeQuery();
        
        while(shipsType.next()){
            type = shipsType.getString(1);
        }
        
        ps.setString(1, type);
        lowestPs.setString(1, type);
        highestPs.setString(1, type);
        
        ResultSet rs = ps.executeQuery();
        ResultSet rs2 = lowestPs.executeQuery();
        ResultSet rs3 = highestPs.executeQuery();

        
        
        while (rs.next())
        {
            average = rs.getDouble(1);
        }
        while (rs2.next())
        {
            lowest = rs2.getDouble(1);
        }
        while (rs3.next())
        {
            highest = rs3.getDouble(1);
        }

        System.out.println(average);
        System.out.println(highest);
        System.out.println(lowest);
        
        gsd.setAverage(average);
        gsd.setHighest(highest);
        gsd.setLowest(lowest);

        return gsd;

    }

    public Ship getLastShipData(int MMSI) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("SELECT ships_mmsi,current_time_ais,x_coordinates,y_coordinates,co2_submission, speed FROM aisinformation WHERE ships_mmsi = ? ORDER BY current_time_ais DESC LIMIT 1;");

        ps.setInt(1, MMSI);

        ResultSet rs = ps.executeQuery();

        double carbonFootprint = 0;
        double latitude = 0;
        double longitude = 0;
        long epoch = 0;
        double speed = 0;

        while (rs.next())
        {
            MMSI = rs.getInt(1);
            epoch = rs.getLong(2);
            latitude = rs.getDouble(3);
            longitude = rs.getDouble(4);
            carbonFootprint = rs.getDouble(5);
            speed = rs.getDouble(6);
        }

        Ship ship = new Ship(MMSI);
        ship.setCarbonFootprint(carbonFootprint);
        ship.setEpochTime(epoch);
        ship.setLatitude(latitude);
        ship.setLongitude(longitude);
        ship.setSpeed(speed);

        return ship;
    }

    public Ship getLastShipData(int MMSI) throws SQLException
    {
        Connection conn = DriverManager.getConnection(myUrl, username, password);
        PreparedStatement ps = conn.prepareStatement("select ships_mmsi,current_time_ais,x_coordinates,y_coordinates,co2_submission, speed from aisinformation WHERE ships_mmsi = ? LIMIT 1;");

        ps.setInt(1, MMSI);
        
        ResultSet rs = ps.executeQuery();

        double carbonFootprint = 0;
        double latitude = 0;
        double longitude = 0;
        Date dateTime = new Date();
        double speed = 0;
        
        while(rs.next()){
            MMSI = rs.getInt(1);
            dateTime = rs.getDate(2);
            latitude = rs.getDouble(3);
            longitude = rs.getDouble(4);
            carbonFootprint = rs.getDouble(5);
            speed = rs.getDouble(6);
        }
        
        Ship ship = new Ship(MMSI);
        ship.setCarbonFootprint(carbonFootprint);
        ship.setDateTime(dateTime);
        ship.setLatitude(latitude);
        ship.setLongitude(longitude);
        ship.setSpeed(speed);

        return ship;
    }

    public ArrayList<String[]> getShipTypesAvailable() throws SQLException
    {

        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

        ArrayList<String[]> list = new ArrayList();

        PreparedStatement ps = conn.prepareStatement("SELECT typename, typebigname FROM shiptype;");

        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            String typeName = rs.getString(1);
            String typeBigName = rs.getString(2);

            String[] typeNames = new String[2];
            typeNames[0] = typeName;
            typeNames[1] = typeBigName;

            list.add(typeNames);
        }

        return list;
    }

    public ArrayList<Ship> getSortedSpeedData(int MMSI) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

<<<<<<< HEAD
        PreparedStatement ps = conn.prepareStatement("SELECT ships_mmsi,speed,current_time_ais,co2_submission from aisinformation WHERE ships_mmsi = ? ORDER BY speed;");
=======
        PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT speed, current_time_ais, co2_submission FROM aisinformation WHERE ships_mmsi = ? ORDER BY speed;");
>>>>>>> 35364143e11227216e6b8c1403b265d30f018d54

        ps.setInt(1, MMSI);

        ResultSet rs = ps.executeQuery();
        ArrayList<Ship> shipList = new ArrayList();
        
        Double checkNewSpeed = 0.0;
        while (rs.next())
        {
<<<<<<< HEAD
            int mmsi = rs.getInt(1);
            double speed = rs.getDouble(2);
            long time = rs.getLong(3);
            double co2 = rs.getDouble(4);

            Ship ship = new Ship(mmsi, time, speed, co2);
=======
            double speed = rs.getDouble(1);
            long time = rs.getLong(2);
            double co2 = rs.getDouble(3);

            Ship ship = new Ship();
            ship.setSpeed(speed);
            ship.setEpochTime(time);
            ship.setCarbonFootprint(co2);
>>>>>>> 35364143e11227216e6b8c1403b265d30f018d54

            if(ship.getSpeed() > checkNewSpeed){
                checkNewSpeed = checkNewSpeed + 0.5;
                shipList.add(ship);
            }
        }

        return shipList;
    }

    public ArrayList<Ship> getShipEmissionLocationData(int MMSI) throws SQLException
    {

        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

<<<<<<< HEAD
        PreparedStatement ps = conn.prepareStatement("select ships_mmsi,current_time_ais,x_coordinates,y_coordinates,co2_submission from aisinformation WHERE ships_mmsi = ? LIMIT 100;");
=======
        PreparedStatement ps = conn.prepareStatement("SELECT ships_mmsi,current_time_ais,x_coordinates,y_coordinates,co2_submission,speed FROM aisinformation WHERE ships_mmsi = ? ORDER BY current_time_ais DESC LIMIT 250;");
>>>>>>> 35364143e11227216e6b8c1403b265d30f018d54

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
            double speed = rs.getDouble(6);
            Ship ship = new Ship(mmsi, x, y, time, emission);

            shipList.add(ship);
        }

        return shipList;

    }

    /**
     *
     * @param user to register, has to be of type User.
     * @return isSuccessful as a boolean.
     * @throws SQLException
     *
     */
    public boolean registerUser(User user) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        boolean oneSuccess = false;
        boolean twoSuccess = false;
        boolean threeSuccess = false;

        boolean isSuccessful = false;

        PreparedStatement ps = conn.prepareStatement("INSERT INTO usersaccount(username, password, email) VALUES(?, ?, ?);");
        PreparedStatement ps2 = conn.prepareStatement("INSERT INTO ships(mmsi, shiptype_typename) VALUES(?, ?);");
        PreparedStatement ps3 = conn.prepareStatement("INSERT INTO usersaccount_has_ships(usersaccount_username, ships_mmsi) VALUES(?, ?);");

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());

        ps2.setInt(1, user.getMMSI());
        ps2.setString(2, user.getShipType());

        ps3.setString(1, user.getUsername());
        ps3.setInt(2, user.getMMSI());

        Savepoint sp = conn.setSavepoint();

        if (ps.execute() == false || true)
        {
            oneSuccess = true;
        }
        if (ps2.execute() == false || true)
        {
            twoSuccess = true;
        }
        if (ps3.execute() == false || true)
        {
            threeSuccess = true;
        }

        // If the query is executed it will return false or true depending on if it is an insert query or not.
        // This means that to ensure we return true when the method succeeds we have to check whether it is either false or true.
        if (oneSuccess && twoSuccess && threeSuccess)
        {
            isSuccessful = true;
            return isSuccessful;
        }
        // if this return is reached, this means the query did not get executed properly. The method will have returned null.
        conn.rollback(sp);
        return isSuccessful;
    }

    /**
     *
     * @param user to validate, has to be of type User.
     * @return a boolean depending on whether the method succeeded or not.
     * @throws SQLException
     */
    public int validateUser(User user) throws SQLException
    {
        Connection conn = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);

        PreparedStatement ps = conn.prepareStatement("SELECT uhs.ships_mmsi FROM usersaccount u, usersaccount_has_ships uhs WHERE u.username = ? AND u.password = ? AND uhs.usersaccount_username = ? LIMIT 1;");

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getUsername());

        int shipMMSI = 0;

        ResultSet rs = ps.executeQuery();
        while (rs.next())
        {
            shipMMSI = rs.getInt(1);
        }
<<<<<<< HEAD

        System.out.println(shipMMSI + " -- MMSI to sent to user.");
        System.out.println(ps.toString() + " -- The prepared statement.");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
=======
>>>>>>> 35364143e11227216e6b8c1403b265d30f018d54

        return shipMMSI;
    }
}
