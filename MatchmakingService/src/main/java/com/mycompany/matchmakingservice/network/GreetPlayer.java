
package com.mycompany.matchmakingservice.network;

import java.io.*;
import java.net.*;

/**
 *
 * @author bergmjul
 */
public class GreetPlayer {
    private Socket playerSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        playerSocket = new Socket(ip, port);
        out = new PrintWriter(playerSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        playerSocket.close();
    }
}

//https://www.baeldung.com/a-guide-to-java-sockets 