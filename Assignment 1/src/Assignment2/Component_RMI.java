package Assignment2;

import java.rmi.Remote;

public interface Component_RMI extends Remote {
    public void receiveToken(Token token);

    public void sendToken(int receiverId);

    public void receiveMessage(int senderId, int sequenceNumber);

    public void broadcastMessage();
}
