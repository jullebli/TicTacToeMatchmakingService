/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.matchmakingservice.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bergmjul
 */
public class SocketConnections {

    private ServerSocket matchmakerSocket;
    private ArrayList<String> clientIPs;

    public void start(int port) throws IOException {
        System.out.println("SocketConnections.start");
        clientIPs = new ArrayList<>();
        matchmakerSocket = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));
        while (true) {
            new clientHandler(matchmakerSocket.accept()).start();
        }
    }

    public void addClientIP(String clientIP) {
        clientIPs.add(clientIP);
    }

    public void stop() throws IOException {
        matchmakerSocket.close();
    }

    private static class clientHandler extends Thread {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private ServerSocket matchmakerSocket;

        public clientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            //this.matchmakerSocket = matchmakerSocket;
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
                        sendMessage("hello client");
                        //UserBuffer.getInstance().add(received);
                    } else if (inputLine.equals("we are connected")) {
                        //matchmakerSocket.addClientIP();
                        System.out.println("getRemoteSocketAddress: "
                                + clientSocket.getRemoteSocketAddress().toString());
                        //if ()
                        sendMessage("You can start game");
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
            //String response = in.readLine();
            System.out.println("Matchmaker sent message " + message);
            //return response;
        }
    }
}
