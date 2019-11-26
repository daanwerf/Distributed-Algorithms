package Assignment2;

import java.rmi.Remote;

public class Component implements Component_RMI {

    private String name;

    public Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
