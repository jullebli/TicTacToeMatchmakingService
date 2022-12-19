package com.mycompany.matchmakingservice.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bergmjul
 */
public class SocketConnections {

    public void start(int port) throws IOException {
        System.out.println("SocketConnections.start");
        ServerSocket matchmakerSocket = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));
        UserQueue userQueue = new UserQueue(3);
        while (true) {
            Socket clientSocket = matchmakerSocket.accept();
            ClientHandler handler = new ClientHandler(clientSocket, userQueue);
            handler.start();
        }
    }

    private static class ClientHandler extends Thread {

        private Socket clientSocket;
        private UserQueue userBuffer;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket, UserQueue userBuffer) {
            this.clientSocket = clientSocket;
            this.userBuffer = userBuffer;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Matchmaker received message: " + inputLine);
                    if (inputLine.equals("")) {
                        sendMessage("bye");
                        break;
                    } else if (inputLine.equals("hello matchmaker")) {
                        userBuffer.addClientSocket(clientSocket);
                        sendMessage("hello client");
                    } else if (inputLine.equals("we are connected")) {
                        System.out.println("getRemoteSocketAddress: "
                                + clientSocket.getRemoteSocketAddress().toString());
                        String multicastInfo = userBuffer.waitForMulticastInfo(clientSocket);
                        sendMessage("You can start game");
                        sendMessage(multicastInfo);
                        break;
                    }
                }
                System.out.println("Server went out of while loop");
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(SocketConnections.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void sendMessage(String message) throws IOException {
            out.println(message);
            System.out.println("Matchmaker sent message " + message);
        }
    }
}
