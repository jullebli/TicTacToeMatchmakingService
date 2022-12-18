/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.matchmakingservice.networking;

import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Sami Vornanen <vornsami>, Julia Bergman <bergmjul>
 */
public class UserQueue {

    private final Queue<Socket> queue;
    private String multicastAddress;
    private int roomSize;
    //private static UserBuffer instance;

    public UserQueue(int roomSize) {
        queue = new LinkedList<>();
        roomSize = roomSize;
    }

    //public static Queue<Socket> getInstance() {
    //    if (instance == null) {
    //        instance = new UserBuffer();
    //    }
    //    return instance.buffer;
    //}
    public synchronized void addClientSocket(Socket clientSocket) {
        //Queue<Socket> queue = UserBuffer.getInstance();
        queue.add(clientSocket);
        
        if (queue.size() < 2) {
            System.out.println("not enough players");
            return;
        }
        //multicastAddress = "224.0.2.50";
        multicastAddress = "230.0.0.0";
        queue.remove();
        queue.remove();
        System.out.println("Removed and Notifying everyone");
        notifyAll();
    }

    public synchronized String waitOrGiveMulticastAddress() {
        while (true) {
            System.out.println("checking queue size");
            if (multicastAddress == null) {
                try {
                    System.out.println("going to wait");
                    wait();
                    System.out.println("waking up");
                } catch (InterruptedException e) {
                    System.out.println("InterruptedExpression: " + e);
                }
            } else {
                System.out.println("returning multicastAddress and Port");
                return multicastAddress;
            }
        }
    }
}
