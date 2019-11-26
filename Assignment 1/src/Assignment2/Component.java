package Assignment2;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Component implements Component_RMI {

    private int componentId;
    private int[] otherComponentIds;
    private boolean hasToken;
    private Token token;

    public Component(int componentId, int[] otherComponentIds, boolean hasToken, int processesAmount) {
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
    public void tokenReceive(Token token) {

    }

    @Override
    public void requestReceive(int senderId, int sequenceNumber) {

    }
}
