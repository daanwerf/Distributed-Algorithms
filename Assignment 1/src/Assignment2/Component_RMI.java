package Assignment2;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Component_RMI extends Remote {
    void receiveToken(Token token) throws RemoteException;

    void sendToken(int receiverId) throws RemoteException, NotBoundException, MalformedURLException;

    void receiveMessage(int senderId, int sequenceNumber) throws RemoteException;

    void broadcastMessage() throws RemoteException, NotBoundException, MalformedURLException;
}
