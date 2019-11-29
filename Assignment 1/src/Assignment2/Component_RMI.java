package Assignment2;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;

public interface Component_RMI extends Remote {
    /**
     * Receive the token from another component. Once the token is received the critical section is entered.
     * @param tokenLN List with sequence numbers in the token.
     * @param tokenQueue Queue with next-in-line components to receive the token.
     * @throws RemoteException
     */
    void receiveToken(int[] tokenLN, Queue<Integer> tokenQueue) throws RemoteException;

    /**
     * Send the token to another component.
     * @param receiverId Id of the component that is to receive the token.
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    void sendToken(int receiverId) throws RemoteException, NotBoundException, MalformedURLException;

    /**
     * Receive a message from another component.
     * @param senderId Id of the sender of the message.
     * @param sequenceNumber Sequence number of the sender of the message.
     * @throws RemoteException
     */
    void receiveMessage(int senderId, int sequenceNumber) throws RemoteException;

    /**
     * Broadcast a message with a request of the token to every other component.
     * @throws RemoteException
     * @throws NotBoundException
     * @throws MalformedURLException
     */
    void broadcastMessage() throws RemoteException, NotBoundException, MalformedURLException;
}
