package org.example.app;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) throws IOException {
        // 1. Create a server socket
        ServerSocket server = new ServerSocket(95);

        // 2. Wait for the connection
        System.out.println("Server started. Waiting for the client....");

        // 3. Get the socket I/O streams and perform the processing
        try (Socket conn = server.accept()) {
            System.out.println("Client connected!!");
            // 3.1 --> InputStream; to receive information from client
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            // Read and print the message from the client
            out.println("Hello from the server!");
            out.flush();  // Flush the output stream to ensure the message is sent

            String str = in.readLine();
            System.out.println("Client has sent: " + str);

            ServerExecute serverExecute = new ServerExecute();

            // 4. Close the connection
            in.close();   // Close the input stream
            out.close();  // Close the output stream
        }
    }
}
