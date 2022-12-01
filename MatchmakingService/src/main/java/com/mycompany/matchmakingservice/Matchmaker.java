
package com.mycompany.matchmakingservice;

import java.io.IOException;
import java.util.ArrayList;
import java.net.*;
import java.io.*;

/**
 *
 * @author bergmjul
 */
public class Matchmaker {
    private ArrayList<String> playerIPs;
    
    public Matchmaker() {
        this.playerIPs = new ArrayList<>();
    }
    
    public void organizeAGame(ArrayList<String> playerIPs) throws IOException {
        String playerIPsString = playerIPs.get(0) + "," + playerIPs.get(1);
    }
    
    public void addNewPlayer(String PlayerIP) throws IOException {
        playerIPs.add(PlayerIP);
        if (playerIPs.size() == 2) {
            organizeAGame(playerIPs);
        }
    }
}
