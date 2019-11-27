package Assignment2;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Process implements Runnable {
    private Component component;

    public Process(Component component) {
        this.component = component;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep((int) (Math.random() * 10000));
                component.broadcastMessage();
            } catch (InterruptedException | RemoteException | NotBoundException | MalformedURLException e) {
                e.printStackTrace();
            }

        }
    }
}
