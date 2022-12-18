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

    public void start(int port) throws IOException {
        System.out.println("SocketConnections.start");
        ServerSocket matchmakerSocket = new ServerSocket(port, 10, InetAddress.getByName("127.0.0.1"));
        UserQueue userQueue = new UserQueue(2);
        while (true) {
            Socket clientSocket = matchmakerSocket.accept();
            ClientHandler handler = new ClientHandler(clientSocket, userQueue);
            handler.start();
        }
    }

    //public void stop() throws IOException {
    //    matchmakerSocket.close();
    //}

    private static class ClientHandler extends Thread {

        private Socket clientSocket;
        private UserQueue userBuffer;
        private PrintWriter out;
        private BufferedReader in;
        // private ServerSocket matchmakerSocket;

        public ClientHandler(Socket clientSocket, UserQueue userBuffer) {
            this.clientSocket = clientSocket;
            this.userBuffer = userBuffer;
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
                        userBuffer.addClientSocket(clientSocket);
                        sendMessage("hello client");
                    } else if (inputLine.equals("we are connected")) {
                        System.out.println("getRemoteSocketAddress: "
                                + clientSocket.getRemoteSocketAddress().toString());
                        String multicastIPAndPort = userBuffer.waitOrGiveMulticastAddress();
                        sendMessage("You can start game");
                        sendMessage(multicastIPAndPort);
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
            //String response = in.readLine();
            System.out.println("Matchmaker sent message " + message);
            //return response;
        }
    }
    
        
 /*   
    try {
            while (RUNNING) {
                if (list.size() >= GAME_SIZE) {
                    try {
                        String message = TARGET_LOCATION;
                        for (int i = 0; i < GAME_SIZE; i++) {
                            message += ";" + list.poll();
                        }

                        publisher.send(message, HOST_LOCATION);
                        //here should send to every needed socket the multicastIP
                        System.out.println("Sent game to " + TARGET_LOCATION);
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
                //    Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
*/
}
