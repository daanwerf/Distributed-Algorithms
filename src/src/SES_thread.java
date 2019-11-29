import java.util.ArrayList;

public class SES_thread implements Runnable {
    private ArrayList<Message> messages;
    private SES processor;

    public SES_thread(SES processor, ArrayList<Message> messages) {
        this.messages = messages;
        this.processor = processor;
    }

    @Override
    public void run() {
        for(Message i: messages){
            try{
                Thread.sleep(1000);
                processor.send(i);
            } catch(Exception e){
                System.err.println("Error Thread:"+ e.toString());
            }
        }
    }
}
