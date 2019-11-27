package Assignment2;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;

public class Component extends UnicastRemoteObject implements Component_RMI {

    private int componentId;
    private int[] otherComponentIds;
    private boolean hasToken;
    private Token token;
    private int processesAmount;
    private int[] RN;

    public Component(int componentId, int[] otherComponentIds, boolean hasToken, int processesAmount) throws RemoteException {
        super();
        this.componentId = componentId;
        this.otherComponentIds = otherComponentIds;
        this.hasToken = hasToken;
        this.token = new Token(processesAmount);
        this.processesAmount = processesAmount;
        this.RN = new int[processesAmount];

        // Bind the Component to the registry
        // TODO: Fix naming, doesn't work like this
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("c" + componentId, this);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public void enterCriticalSection() {
        System.out.println("Component " + componentId + " has entered critical section");
    }

    @Override
    public void receiveToken(Token token) {
        enterCriticalSection();
        token.updateLN(componentId, RN[componentId]);

        Queue<Integer> queue = token.getQueue();
        for(int j = 0; j < processesAmount; j++) {
            if (!queue.contains(j) && RN[j] == token.getFromLN(j) + 1) {
                token.addComponentToQueue(j);
            }
        }
        queue = token.getQueue();
        if (!queue.isEmpty()) {
            int nextComponentInLineId = queue.poll();
            sendToken(nextComponentInLineId);
        } else {
            // Keep the token; do nothing
        }
    }

    @Override
    public void sendToken(int receiverId) {
        try {
            Component_RMI receiver = (Component_RMI) Naming.lookup(makeName(receiverId));
            receiver.receiveToken(this.token);
            this.hasToken = false;
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(int senderId, int sequenceNumber) {
        this.RN[senderId] = Integer.max(RN[senderId], sequenceNumber);

        System.out.println("Own sequenceNumber: " + RN[senderId] + ". sender sequenceNumber: " + token.getFromLN(senderId));

        if (hasToken && RN[senderId] == token.getFromLN(senderId) + 1) {
            sendToken(senderId);
            System.out.println("Component " + componentId + " sent token to " + Integer.toString(senderId));
        }
        System.out.println("Component" + componentId + " has received a message from " + senderId);
    }

    @Override
    public void broadcastMessage() throws RemoteException, NotBoundException, MalformedURLException {
        this.RN[componentId]++;

        System.out.println("Component " + componentId + " will broadcast a request");
        for (int i = 0; i < processesAmount; i++) {
            Component_RMI receiver =  (Component_RMI) Naming.lookup(makeName(i));
            receiver.receiveMessage(componentId, RN[componentId]);
        }
    }

    @Override
    public String toString() {
        return "componentId " + componentId + " hasToken: " + hasToken;
    }


    public String makeName(int id) {
        return "//localhost:1099/c" + Integer.toString(id);
    }
}
