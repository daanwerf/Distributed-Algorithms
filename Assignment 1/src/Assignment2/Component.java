package Assignment2;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Component extends UnicastRemoteObject implements Component_RMI {

    private int componentId;
    private int[] otherComponentIds;
    private boolean hasToken;
    private Token token;

    public Component(int componentId, int[] otherComponentIds, boolean hasToken, int processesAmount) throws RemoteException {
        super();
        this.componentId = componentId;
        this.otherComponentIds = otherComponentIds;
        this.hasToken = hasToken;
        this.token = new Token(processesAmount);

        // Bind the Component to the registry
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

    }

    @Override
    public void broadcastMessage() {
        System.out.println("Component " + Integer.toString(componentId) + " says hi");
    }
}
