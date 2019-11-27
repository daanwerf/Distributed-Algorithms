package Assignment2;

import java.rmi.RemoteException;

public class DA_SuzukiKazami_main {
    public static void main(String args[]) {
        // TODO: change back to args[0] when done
        int processesAmount = Integer.parseInt("5");
        Thread[] threads = new Thread[processesAmount];

        try {
            // Create the registry
            java.rmi.registry.LocateRegistry.createRegistry(1099);

            // Create all components and bind them to threads, component 0 gets the token
            for (int i = 0; i < processesAmount; i++) {
                    Component c = new Component(i, i == 0, processesAmount);
                    threads[i] = new Thread(new Process(c));
                    threads[i].start();
            }



        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
