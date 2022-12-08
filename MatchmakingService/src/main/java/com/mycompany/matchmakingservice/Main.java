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
    private static final int GAME_SIZE = 4;
    private static final String HOST_LOCATION = "230.0.0.0";
    private static final String TARGET_LOCATION = "240.0.0.0";
    private static boolean RUNNING = true;
    
    public static void main(String[] args) {
        
        MulticastReceiver receiver = new MulticastReceiver();
        receiver.start();
        
        NetworkPublisher publisher = new MulticastPublisher();
        
        Queue<String> list = UserBuffer.getInstance();
        try {
        while(RUNNING) {
            if(list.size() >= GAME_SIZE) {
                try {
                    publisher.send(TARGET_LOCATION, HOST_LOCATION);
                    for(int i=0;i<GAME_SIZE;i++) list.poll();
                    
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
