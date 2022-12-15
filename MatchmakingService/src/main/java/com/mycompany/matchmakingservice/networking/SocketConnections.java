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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bergmjul
 */
public class SocketConnections {

    private ServerSocket matchmakerSocket;

    public void start(int port) throws IOException {
        matchmakerSocket = new ServerSocket(port);
        while (true) {
            new clientHandler(matchmakerSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        matchmakerSocket.close();
    }

    private static class clientHandler extends Thread {

        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public clientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Matchmaker received: " + inputLine);
                    if (inputLine.equals("")) {
                        out.println("bye");
                        break;
                    } else if (inputLine.equals("hello matchmaker")) {
                        out.println("hello client");
                        //UserBuffer.getInstance().add(received);
                    }
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(SocketConnections.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
