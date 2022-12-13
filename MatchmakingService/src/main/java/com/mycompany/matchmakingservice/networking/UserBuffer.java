/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.matchmakingservice.networking;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Sami Vornanen <vornsami>
 */
public class UserBuffer  {
    private final Queue<String> buffer;
    private static UserBuffer instance;
    
    private UserBuffer() {
        buffer = new LinkedList<>();
    }
    
    public static Queue<String> getInstance() {
        if (instance == null) instance = new UserBuffer();
        
        return instance.buffer;
    }
    
}
