package Assignment2;

public class Process implements Runnable {
    private Component component;

    public Process(Component component) {
        this.component = component;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep((int) Math.random() * 2500);
                component.broadcastMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
