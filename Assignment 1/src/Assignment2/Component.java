package Assignment2;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Component extends UnicastRemoteObject implements Component_RMI {

    private int componentId;
    private boolean hasToken;
    private int processesAmount;
    private int[] RN;
    private int[] LN;
    private Queue<Integer> queue;
    private boolean inCriticalSection;

    public Component(int componentId, boolean hasToken, int processesAmount) throws RemoteException {
        super();
        this.componentId = componentId;
        this.hasToken = hasToken;
        this.LN = new int[processesAmount];
        this.queue = new ArrayDeque<>(processesAmount);
        this.processesAmount = processesAmount;
        this.RN = new int[processesAmount];
        this.inCriticalSection = false;

        // Bind the Component to the registry
        try {
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("c" + componentId, this);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public void enterCriticalSection() {
        try {
            System.out.println("Component " + componentId + " has entered critical section");
            inCriticalSection = true;
            Thread.sleep((int) (Math.random() * 10000));
            System.out.println("Component " + componentId + " has left critical section");
            inCriticalSection = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveToken(int[] tokenLN, Queue<Integer> tokenQueue) {
        hasToken = true;
        System.out.println("Component " + componentId + " has received the token");
        enterCriticalSection();
        LN[componentId] = RN[componentId];
        this.LN = tokenLN;
        this.queue = tokenQueue;

        // TODO: Fix the queue (probably not needed for deadline, but would be fun to have)
//        for(int j = 0; j < processesAmount; j++) {
//            if (!queue.contains(j) && RN[j] > LN[j]) {
//                queue.add(j);
//            }
//        }
//        System.out.println("Current token queue: " + queue);
//        if (!queue.isEmpty()) {
//            int nextComponentInLineId = queue.poll();
//            sendToken(nextComponentInLineId);
//        } else {
//            // Keep the token; do nothing
//        }
    }

    @Override
    public void sendToken(int receiverId) {
        try {
            Component_RMI receiver = (Component_RMI) Naming.lookup(makeName(receiverId));
            this.hasToken = false;
            System.out.println("Component " + componentId + " sends the token to component " + Integer.toString(receiverId));
            receiver.receiveToken(LN, queue);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(int senderId, int sequenceNumber) {
        this.RN[senderId] = Integer.max(RN[senderId], sequenceNumber);

        // System.out.println("Component" + componentId + " has received a message from " + senderId);
        if (hasToken && !inCriticalSection && RN[senderId] > LN[senderId]) {
            sendToken(senderId);
        }
    }

    @Override
    public void broadcastMessage() throws RemoteException, NotBoundException, MalformedURLException {
        if (!hasToken && !inCriticalSection) {
            this.RN[componentId]++;
            System.out.println("Component " + componentId + " broadcasts a request");
            for (int i = 0; i < processesAmount; i++) {
                Component_RMI receiver =  (Component_RMI) Naming.lookup(makeName(i));
                receiver.receiveMessage(componentId, RN[componentId]);
            }
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
