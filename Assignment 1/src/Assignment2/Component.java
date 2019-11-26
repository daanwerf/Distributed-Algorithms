package Assignment2;

import java.rmi.Remote;

public class Component implements Component_RMI {

    private int componentId;
    private String[] ipAddresses;
    private boolean hasToken;
    private Token token;

    public Component(int componentId, String[] ipAddresses, boolean hasToken, int processesAmount) {
        super();
        this.componentId = componentId;
        this.ipAddresses = ipAddresses;
        this.hasToken = hasToken;
        this.token = new Token(processesAmount);
    }

    @Override
    public void tokenReceive(Token token) {

    }

    @Override
    public void requestReceive(int senderId, int sequenceNumber) {

    }
}
