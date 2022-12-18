
package com.mycompany.matchmakingservice;

import com.mycompany.matchmakingservice.networking.SocketConnections;
import java.io.IOException;

/**
 *
 * @author bergmjul
 */
public class Main {

    public static void main(String[] args) {

        SocketConnections sockets = new SocketConnections();
        try {
            System.out.println("Try sockets.start(6666)");
            sockets.start(6666);
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}
