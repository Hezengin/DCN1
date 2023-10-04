package nl.avans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {
        try (
                Socket kkSocket = new Socket("localhost", 223);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                //System.out.println("Server: " + fromServer);

                if (fromServer.equals("Knock! Knock! Give ISBN")) {
                    // User input ISBN
                    fromUser = stdIn.readLine();
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                } else {
                    // Print server's response
                    System.out.println(fromServer);
                }

                if (fromServer.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    "localhost");
            System.exit(1);
        }
    }
}
