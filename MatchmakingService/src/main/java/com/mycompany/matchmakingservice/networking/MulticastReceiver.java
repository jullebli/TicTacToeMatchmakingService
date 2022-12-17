
package com.mycompany.matchmakingservice.networking;

import java.io.IOException;
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
    private String host;
    
    public MulticastReceiver(String defaultHost) {
        this.host = defaultHost;
    }
    
    @Override
    public void run() {
        try {
        socket = new MulticastSocket(4446);
        InetAddress group = InetAddress.getByName(host);
        socket.joinGroup(group);
        
        while (true) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(
              packet.getData(), 0, packet.getLength());
            if ("end".equals(received)) {
                break;
            } else if (received.length() == 10 && !received.contains(",")) {
                System.out.println(received);
                UserBuffer.getInstance().add(received);
            }
        }
        socket.leaveGroup(group);
        socket.close();
        } catch (IOException e) {
            System.out.println("EXCEPTION in MulticastReceiver" + e);
        }
    }
}