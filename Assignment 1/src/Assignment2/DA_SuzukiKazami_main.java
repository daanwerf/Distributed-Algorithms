package Assignment2;

import java.rmi.RemoteException;

public class DA_SuzukiKazami_main {
    public static void main(String args[]) {
        int processesAmount = Integer.parseInt(args[0]);
        Thread[] threads = new Thread[processesAmount];

        try {
            // Create the registry
            java.rmi.registry.LocateRegistry.createRegistry((1099));

            // Create array with all the component ids, 0 to processesAmount
            int[] otherComponentIds = new int[processesAmount];
            for (int i = 0; i < processesAmount; i++) {
                otherComponentIds[i] = i;
            }

            // Create all components, component 0 gets the token
            for (int i = 0; i < processesAmount; i++) {
                if (i == 0) {
                    Component c = new Component(i, otherComponentIds, true, processesAmount);
                } else {
                    Component c = new Component(i, otherComponentIds, false, processesAmount);
                }
            }




        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
