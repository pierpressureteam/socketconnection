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
import static socketserver.DatabaseConnection.myUrl;
import static socketserver.DatabaseConnection.password;
import static socketserver.DatabaseConnection.username;
import objectslibrary.User;

/**
 *
 * @author Roy van den Heuvel
 */
public class SocketServer {

    static final int PORT = 32000;

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("PORT IN USE: " + PORT);
        startDataServer();
    }

    public static void startDataServer() throws IOException, SQLException {

        System.out.println("Data socket started.");

        while (true) {
            try {
                // Create the Client Socket
                ServerSocket dataSocketIn = new ServerSocket(PORT);
                Socket clientSocketIn = dataSocketIn.accept();

                // Create input and output streams to client
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocketIn.getOutputStream());
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocketIn.getInputStream());

                Object checkedObject = checkObject(inFromClient.readObject());

                outToClient.writeObject(checkedObject);

            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    public static Object checkObject(Object obj) throws SQLException {

        ArrayList arrayFrmObj = (ArrayList) obj;

        if (arrayFrmObj != null) {
            int intFrmObj = (Integer) arrayFrmObj.get(0);

            if (intFrmObj == 1) {
                //User validation
                User userToValidate = (User) arrayFrmObj.get(1);
                return validateUser(userToValidate);
            }
            if (intFrmObj == 2) {
                // User registration
                User userToRegister = (User) arrayFrmObj.get(1);
                return registerUser(userToRegister);
            }
            if (intFrmObj == 3) {
                int i = 3;
                return i;
            }
        }
        return null;
    }
    
    
    public static boolean registerUser(Object obj) throws SQLException{
        Connection conn = DriverManager.getConnection(myUrl, username, password);
        
        PreparedStatement ps = conn.prepareStatement("INSERT INTO usersaccount(username, password, email) VALUES(?, ?, ?) RETURNING username");
        
        User user = (User) obj;
        
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getEmail());
        
        ResultSet rs = ps.executeQuery();
      
        return rs.first();
    }

    public static boolean validateUser(Object obj) throws SQLException {
        Connection conn = DriverManager.getConnection(myUrl, username, password);

        PreparedStatement ps = conn.prepareStatement("SELECT u.username FROM usersaccount u WHERE username = ? AND password = ?");

        User user = (User) obj;

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());

        ps.execute();
        ResultSet rs = ps.getResultSet();
        
        return rs.first();
    }
}
