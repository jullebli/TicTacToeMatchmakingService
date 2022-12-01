package com.mycompany.matchmakingservice.network;

import com.mycompany.matchmakingservice.Matchmaker;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author bergmjul
 */
public class MulticastReceiver extends Thread {

    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
    private String address;
    private Matchmaker matchmaker;

    public MulticastReceiver(String address, Matchmaker matchmaker) {
        this.address = address;
        this.matchmaker = matchmaker;
    }

    public void run() {
        try {
            socket = new MulticastSocket(4446);
            InetAddress group = InetAddress.getByName(address);
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                if ("end".equals(received)) {
                    break;
                }
                
                String[] parts = received.split(",");
                
                if (parts.length == 1) {
                    System.out.println(received);
                    matchmaker.addNewPlayer(parts[0]);
                    
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            System.out.println("EXCEPTION" + e);
        }
    }
}
