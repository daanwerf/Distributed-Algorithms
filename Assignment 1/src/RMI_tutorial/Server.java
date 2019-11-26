package RMI_tutorial;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

    public Server() {}

    public String sayHello() {
        return "RMI_tutorial.Hello, world!";
    }

    public static void main(String args[]) {

        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("RMI_tutorial.Hello", stub);

            System.err.println("RMI_tutorial.Server ready");
        } catch (Exception e) {
            System.err.println("RMI_tutorial.Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}