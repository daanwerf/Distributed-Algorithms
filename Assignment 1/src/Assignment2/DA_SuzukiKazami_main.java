package Assignment2;

import java.rmi.RemoteException;

public class DA_SuzukiKazami_main {
    public static void main(String args[]) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry((1099));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
