package socketserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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


    public static void startDataServer() throws IOException {

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

    public static Object checkObject(Object obj) {
        if (obj != null) {
            int intFrmObj = (Integer) obj;

            if (intFrmObj == 1) {
                int i = 1;
                return i;
            }
            if (intFrmObj == 2) {
                int i = 2;
                return i;
            }
            if (intFrmObj == 3) {
                int i = 3;
                return i;
            }
        }
        return 100;
    }

    public static boolean validateUser(Object obj) throws SQLException {
        Connection conn = DriverManager.getConnection(myUrl, username, password);

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM usersaccount WHERE username = ? AND password = ?");

        User user = (User) obj;

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());

        return false;
    }
}
