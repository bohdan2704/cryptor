package org.example.app;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        // 1. Create a socket to connect to the server
        try {

            // 2. Get the socket I/O streams and perform the processing
            try (Socket conn = new Socket("localhost", 95)) {
                // 2.1 --> InputStream; to receive information from the server
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                PrintWriter out = new PrintWriter(conn.getOutputStream());

                // Send a message to the server
                out.println("Hello from the client!");
                out.flush();  // Flush the output stream to ensure the message is sent

                // Read and print the message from the server
                String serverResponse = in.readLine();
                System.out.println("Server has sent: " + serverResponse);

                ClientExecute clientExecute = new ClientExecute();

                // 3. Close the connection
                in.close();   // Close the input stream
                out.close();  // Close the output stream
            }
            // Always close the socket after use
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
