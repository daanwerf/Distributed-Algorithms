package Assignment2;

import java.rmi.Remote;

public interface Component_RMI extends Remote {
    public void tokenReceive(Token token);

    public void requestReceive(int senderId, int sequenceNumber);
}
