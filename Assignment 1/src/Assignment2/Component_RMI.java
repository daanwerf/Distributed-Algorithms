package Assignment2;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Component_RMI extends Remote {
    public void receiveToken(Token token) throws RemoteException;;

    public void sendToken(int receiverId) throws RemoteException;;

    public void receiveMessage(int senderId, int sequenceNumber) throws RemoteException;;

    public void broadcastMessage() throws RemoteException, NotBoundException, MalformedURLException;;
}
