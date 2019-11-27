package Assignment2;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;

public interface Component_RMI extends Remote {
    void receiveToken(int[] tokenLN, Queue<Integer> tokenQueue) throws RemoteException;

    void sendToken(int receiverId) throws RemoteException, NotBoundException, MalformedURLException;

    void receiveMessage(int senderId, int sequenceNumber) throws RemoteException;

    void broadcastMessage() throws RemoteException, NotBoundException, MalformedURLException;
}
