package Assignment2;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Component extends UnicastRemoteObject implements Component_RMI {

    private int componentId;
    private int[] otherComponentIds;
    private boolean hasToken;
    private Token token;
    private int processesAmount;
    private int[] componentSequenceNumberList;

    public Component(int componentId, int[] otherComponentIds, boolean hasToken, int processesAmount) throws RemoteException {
        super();
        this.componentId = componentId;
        this.otherComponentIds = otherComponentIds;
        this.hasToken = hasToken;
        this.token = new Token(processesAmount);
        this.processesAmount = processesAmount;
        this.componentSequenceNumberList = new int[processesAmount];

        // Bind the Component to the registry
        // TODO: Fix naming, doesn't work like this
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(Integer.toString(componentId), this);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveToken(Token token) {

    }

    @Override
    public void sendToken(int receiverId) {

    }

    @Override
    public void receiveMessage(int senderId, int sequenceNumber) {
        this.componentSequenceNumberList[senderId] = Integer.max(componentSequenceNumberList[senderId], sequenceNumber);

        if (hasToken && componentSequenceNumberList[senderId] == token.getSequenceNumber(senderId)) {
            sendToken(senderId);
            System.out.println("Sent token to " + Integer.toString(senderId));
        }
    }

    @Override
    public void broadcastMessage() throws RemoteException, NotBoundException, MalformedURLException {
        this.componentSequenceNumberList[componentId]++;

        for(int i = 0; i < processesAmount; i++) {
            // TODO: Fix naming, doesn't work like this
            Component_RMI receiver =  (Component_RMI) Naming.lookup(Integer.toString(i));
            System.out.println(receiver.toString());
            receiver.receiveMessage(componentId, componentSequenceNumberList[componentId]);
        }
    }

    @Override
    public String toString() {
        return "componentId " + componentId + " hasToken: " + hasToken;
    }
}
