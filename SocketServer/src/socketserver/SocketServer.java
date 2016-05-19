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

    static final int DATAPORTIN = 32000;
    static final int LOGINPORTIN = 32001;
    static final int DATAPORTOUT = 32003;
    static final int LOGINPORTOUT = 32004;

    public static void main(String[] args) throws IOException, SQLException {

        System.out.println("DATAPORT: " + DATAPORTIN);
        System.out.println("LOGINPORT: " + LOGINPORTIN);
        
//        startLoginServer();
        startDataServer();

    }

//    public static void startLoginServer() throws IOException {
////        Thread thread = new Thread() {
////            @Override
////            public void run() {
//                System.out.println("Login socket started.");
//                while (true) {
//                    try {
//                        // Create the Client Socket
//                        ServerSocket loginSocketIn = new ServerSocket(LOGINPORTIN);
//                        ServerSocket loginSocketOut = new ServerSocket(LOGINPORTOUT);
//                        Socket clientSocketOut = loginSocketOut.accept();
//                        Socket clientSocketIn = loginSocketIn.accept();
//                        System.out.println("HOIW!I!");
//                        // Create input and output streams to client
//                        ObjectOutputStream outToClient = new ObjectOutputStream(clientSocketOut.getOutputStream());
//                        ObjectInputStream inFromClient = new ObjectInputStream(clientSocketIn.getInputStream());
//
//                        System.out.println("HOII!!!");
//                        
//                        boolean canUserContinue = validateUser(inFromClient.readObject());
//
//                        outToClient.writeObject(canUserContinue);
//                    } catch (IOException | ClassNotFoundException | SQLException ex) {
//                        ex.printStackTrace();
//                    }
//                }
////            }
////        };
////        thread.start();
//    }

    public static void startDataServer() throws IOException {

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
                System.out.println("Data socket started.");

                while (true) {
                    try {
                        // Create the Client Socket
                        ServerSocket dataSocketOut = new ServerSocket(DATAPORTOUT);
                        ServerSocket dataSocketIn = new ServerSocket(DATAPORTIN);
                        Socket clientSocketOut = dataSocketOut.accept();
                        Socket clientSocketIn = dataSocketIn.accept();

                        
                        System.out.println("HOI");
                        
                        // Create input and output streams to client
                        ObjectOutputStream outToClient = new ObjectOutputStream(clientSocketOut.getOutputStream());
                        ObjectInputStream inFromClient = new ObjectInputStream(clientSocketIn.getInputStream());

                        Object checkedObject = checkObject(inFromClient.readObject());

                        System.out.println("HOI!");
                        
                        outToClient.writeObject(checkedObject);
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                }
//            }
//        };
//        thread.start();

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
