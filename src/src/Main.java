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
                int[] timestamp = new int[processors];
                System.err.println(i);
                SES process = new SES(processors,i);
                ArrayList<Message> p = new ArrayList<>();
                if(i == 0) {
                    Message p0_p1 = new Message(1, "0", process.getLocalBuffer(),timestamp, 0);
                    p.add(p0_p1);
                    Message p0_p2 = new Message(2, "0", process.getLocalBuffer(),timestamp, 1000);
                    System.err.println(p0_p2.toString());
                    p.add(p0_p2);


                }
                if(i == 1) {
                    Buffer adder = new Buffer(processors, 2);
                    int[] time = {1,0,0};
                    adder.setTimestamp(time);
                    process.getLocalBuffer().add(adder);
                    Message p1_p2 = new Message(2, "1", process.getLocalBuffer(),timestamp, 500);
                    p.add(p1_p2);

//                    Message p1_p22 = new Message(2, "1", process.getLocalBuffer(),timestamp, 1000);
//                    p.add(p1_p22);
                }
//                if(i == 2) {
//                    Message p2_p1 = new Message(1, "2", process.getLocalBuffer(),timestamp, 500);
//                    p.add(p2_p1);
//                }
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
