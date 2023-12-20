package org.example.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Sniffer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Proxy listening on port 8080...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected...");

                Socket serverSocketToClient = new Socket("localhost", 95);
                System.out.println("Connected to server...");

                new Thread(() -> handleConnection(clientSocket, serverSocketToClient)).start();
                new Thread(() -> handleConnection(serverSocketToClient, clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket from, Socket to) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(from.getInputStream()));
            PrintWriter out = new PrintWriter(to.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Message: " + line);
                out.println(line);
            }

            in.close();
            out.close();
            from.close();
            to.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
