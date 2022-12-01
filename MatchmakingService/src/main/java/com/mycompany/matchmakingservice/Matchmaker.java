
package com.mycompany.matchmakingservice;

import com.mycompany.matchmakingservice.network.MulticastPublisher;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author bergmjul
 */
public class Matchmaker {
    private MulticastPublisher publisher;
    private ArrayList<String> playerIPs;
    
    public Matchmaker(MulticastPublisher publisher) {
        this.publisher = publisher;
        this.playerIPs = new ArrayList<>();
    }
    
    public void organizeAGame(ArrayList<String> playerIPs) throws IOException {
        String playerIPsString = playerIPs.get(0) + "," + playerIPs.get(1);
        publisher.multicast(playerIPsString);
    }
    
    public void addNewPlayer(String PlayerIP) throws IOException {
        playerIPs.add(PlayerIP);
        if (playerIPs.size() == 2) {
            organizeAGame(playerIPs);
        }
    }
}
