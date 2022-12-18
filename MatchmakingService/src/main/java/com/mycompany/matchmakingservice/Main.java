/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.matchmakingservice;

import com.mycompany.matchmakingservice.networking.MulticastPublisher;
import com.mycompany.matchmakingservice.networking.MulticastReceiver;
import com.mycompany.matchmakingservice.networking.NetworkPublisher;
import com.mycompany.matchmakingservice.networking.SocketConnections;
import com.mycompany.matchmakingservice.networking.UserQueue;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bergmjul
 */
public class Main {

    //private static final int GAME_SIZE = 2;
    //private static final String HOST_LOCATION = "230.0.0.0";
    //private static final String TARGET_LOCATION = "230.0.0.1";
    //private static boolean RUNNING = true;

    public static void main(String[] args) {

        //MulticastReceiver receiver = new MulticastReceiver();
        //receiver.start();
        //NetworkPublisher publisher = new MulticastPublisher();
        //Queue<String> list = UserBuffer.getInstance();
        SocketConnections sockets = new SocketConnections();
        try {
            System.out.println("Try sockets.start(6666)");
            sockets.start(6666);
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}
