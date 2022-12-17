
package com.mycompany.matchmakingservice.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author bergmjul
 */
public class MulticastPublisher implements NetworkPublisher{
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
    private String defaultHost;

    public MulticastPublisher(String defaultHost) {
        this.defaultHost = defaultHost;
    }
    
    /**
     * Multicasts a message to a default IP address 230.0.0.0
     * @param multicastMessage
     * @throws IOException
     */
    public void multicast(String multicastMessage) throws IOException {
        send(multicastMessage, defaultHost);
    }
    
    @Override
    public void send(String multicastMessage, String host) throws IOException {
        socket = new DatagramSocket();
        group = InetAddress.getByName(host);
        buf = multicastMessage.getBytes();

        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        socket.close();
    }
}
