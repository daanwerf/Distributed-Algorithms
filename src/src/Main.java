import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int processors = 3;
        Thread[] allThreads = new Thread[processors];

        try{
            Registry reg = LocateRegistry.createRegistry(1099);
            for(int i = 0; i<processors;i++){
                SES process = new SES(i,processors);
                ArrayList<Message> p = new ArrayList<>();
                if(i == 0) {
                    Message p0_p2 = new Message(2, "0", process.getLocalBuffer());
                    p.add(p0_p2);
                }
                if(i == 1) {
                    Message p1_p2 = new Message(2, "1", process.getLocalBuffer());
                    p.add(p1_p2);
                }
                SES_thread p_thread = new SES_thread(process,p);
                allThreads[i] = new Thread(p_thread);
            }
            for(int i = 0; i<processors;i++){
                allThreads[i].start();
            }
        }catch (Exception e){
            System.out.println("Error");
        }
    }
}
