package org.example.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;

public class Client {
    public static final int DESIRED_AES_KEY_LEN = 256;
    private static final Logger logger = LogManager.getLogger(Client.class);

    public static void main(String[] args) {
        // 1. Create a socket to connect to the server
        try {

            // 2. Get the socket I/O streams and perform the processing
            try (Socket conn = new Socket("localhost", 95)) {
                // 2.1 --> InputStream; to receive information from the server
                PrintWriter out = null;
//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                BufferedReader in = null;

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(conn.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(conn.getInputStream());

                String messageToSend = "Hello from the server!";
                objectOutputStream.writeObject(messageToSend);
                objectOutputStream.flush();

                // Read a String object from the server
                Object receivedObject = objectInputStream.readObject();
                // Read and print the message from the server
                String receivedString = (String) receivedObject;
                System.out.println("Received String from server: " + receivedString);

                ClientExecute clientExecute = new ClientExecute();
                BigInteger notTrimmedKeyFromECC = clientExecute.executeECDH(out, objectOutputStream, in, objectInputStream);
                String encryptedMsg = (String)objectInputStream.readObject();
                String s = clientExecute.executeAESDecrypt(notTrimmedKeyFromECC, encryptedMsg);
                System.out.println(s);

                int publicKey = (int)objectInputStream.readObject();

                int exitingCode = objectInputStream.read();
                // 3. Close the connection
//                in.close();   // Close the input stream
                out.close();  // Close the output stream
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // Always close the socket after use
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
