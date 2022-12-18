/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.matchmakingservice;

import com.mycompany.matchmakingservice.networking.MulticastPublisher;
import com.mycompany.matchmakingservice.networking.MulticastReceiver;
import com.mycompany.matchmakingservice.networking.NetworkPublisher;
import com.mycompany.matchmakingservice.networking.UserBuffer;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author bergmjul
 */
public class Main {
    private static final int GAME_SIZE = 3;
    private static final String HOST_LOCATION = "230.0.0.0";
    private static final String TARGET_LOCATION_BASE = "230.0.0.";
    private static int numberOfRooms;
    private static boolean RUNNING = true;
    
    public static void main(String[] args) {
        
        MulticastReceiver receiver = new MulticastReceiver(HOST_LOCATION);
        receiver.start();
        
        NetworkPublisher publisher = new MulticastPublisher(HOST_LOCATION);
        
        Queue<String> list = UserBuffer.getInstance();
        
        numberOfRooms = 0;
        try {
        while(RUNNING) {
            if(list.size() >= GAME_SIZE) {
                try {
                    int tempNumberOfRooms = (numberOfRooms >= 255)? 0 : (numberOfRooms + 1);
                    String message = TARGET_LOCATION_BASE + tempNumberOfRooms;
                    for(int i=0;i<GAME_SIZE;i++) message += "," + list.poll();
                    
                    publisher.send(message, HOST_LOCATION);
                    numberOfRooms = tempNumberOfRooms;
                    
                    System.out.println("Sent game to " + TARGET_LOCATION_BASE + numberOfRooms);
                    
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            
            Thread.sleep(100);
        }} catch (InterruptedException e) {
            System.out.println(e);
        }
        
    }
}
