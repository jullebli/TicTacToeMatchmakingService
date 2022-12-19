
package com.mycompany.matchmakingservice.networking;

import java.net.Socket;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Sami Vornanen <vornsami>, Julia Bergman <bergmjul>
 */
public class UserQueue {

    private final List<Socket> queue;
    private final List<String> userHashes;
    private String multicastAddress;
    private int roomSize;
    //private static UserBuffer instance;

    public UserQueue(int roomSize) {
        userHashes = new LinkedList<>();
        queue = new LinkedList<>();
        this.roomSize = roomSize; //to be implemented
    }

   /*
    public static Queue<Socket> getInstance() {
        if (instance == null) {
            instance = new UserBuffer();
        }
        return instance.buffer;
    }
*/
    public synchronized void addClientSocket(Socket clientSocket) {
        //Queue<Socket> queue = UserBuffer.getInstance();
        queue.add(clientSocket);
        userHashes.add(generateUserHash());

        if (queue.size() < roomSize) {
            System.out.println("not enough players");
            return;
        }
        //multicastAddress = "224.0.2.50";
        multicastAddress = "230.0.0.0";
        // queue.remove(0);
        // userHashes.remove(0); CAN HANDLE ONLY FIRST GAME! FIX IF WANT MORE!
        // queue.remove(0);
        // userHashes.remove(0);
        System.out.println("Removed and Notifying everyone");
        notifyAll();
    }

    public synchronized String waitForMulticastInfo(Socket clientSocket) {
        while (true) {
            System.out.println("checking queue size");
            if (multicastAddress == null) {
                try {
                    System.out.println("going to wait");
                    wait();
                    System.out.println("waking up");
                } catch (InterruptedException e) {
                    System.out.println("InterruptedExpression: " + e);
                }
            } else {
                System.out.println("returning multicastIP");
                int index = queue.indexOf(clientSocket);
                //String userHash = userHashes.get(index);
                String result = multicastAddress + "," + index + "," + String.join(":", userHashes);
                return result;
            }
        }
    }

    private String generateUserHash() {
        Random random = new SecureRandom();
        char[] result = new char[10];
        char[] characters = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();

        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randInt = random.nextInt(characters.length);
            result[i] = characters[randInt];
        }
        return new String(result);
    }
}
