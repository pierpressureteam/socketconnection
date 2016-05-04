/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quicktestsocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Roy van den Heuvel
 */
public class QuickTestSocket {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testSocket();
    }

    public static void testSocket() {
        try {

            String message = "TEST!";
            String serverIp = "145.24.222.149";

            Socket client = new Socket(serverIp, 4444);  //connect to server
            PrintWriter printwriter = new PrintWriter(client.getOutputStream(), true);
            printwriter.write(message);  //write the message to output stream

            printwriter.flush();
            printwriter.close();
            client.close();   //closing the connection

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}