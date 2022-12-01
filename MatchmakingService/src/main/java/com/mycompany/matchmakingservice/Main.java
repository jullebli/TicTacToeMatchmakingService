
package com.mycompany.matchmakingservice;

import com.mycompany.matchmakingservice.network.MulticastPublisher;
import com.mycompany.matchmakingservice.network.MulticastReceiver;

/**
 *
 * @author bergmjul
 */
public class Main {
    
    public static void main(String[] args) {

        String address = "230.0.0.0";
        MulticastPublisher matchPublisher = new MulticastPublisher(address);
        Matchmaker matchmaker = new Matchmaker(matchPublisher);
        MulticastReceiver matchReceiver = new MulticastReceiver(address, matchmaker);
        Thread t = new Thread(matchReceiver);
        t.start();
    }
}
