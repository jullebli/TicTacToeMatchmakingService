/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.matchmakingservice.networking;

import java.io.IOException;

/**
 *
 * @author Sami Vornanen <vornsami>
 */
public interface NetworkPublisher {

    /**
     * Sends the given message to the given host's ip-address.
     * Host has to be a string containing a valid ip-address
     * 
     * @param message
     * @param host
     * @throws IOException
     */
    public void send(String message, String host)throws IOException;
}
