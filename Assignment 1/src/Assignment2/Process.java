package Assignment2;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Process implements Runnable {
    private Component component;

    public Process(Component component) {
        this.component = component;
    }

    /**
     * Simulate the component broadcasting by sleeping for anywhere between 1 to 10 seconds and then broadcasting
     * a request.
     */
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
