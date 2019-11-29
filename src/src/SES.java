import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;

public class SES extends UnicastRemoteObject implements SES_RMI{
    private int[] processors;
    private int current_processor;
    private ArrayList<Message> messageBuffer;
    private ArrayList<Buffer> localBuffer;

    public SES(int processors, int current_processor) throws RemoteException{
        super();
        this.processors = new int[processors];
        this.current_processor = current_processor;
        messageBuffer = new ArrayList<Message>();
        localBuffer = new ArrayList<Buffer>();
        //System.err.println(current_processor);
        try{
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("SES-"+current_processor, this);
            System.err.println("SES-"+current_processor+" running");
        } catch (Exception e) {
        System.err.println("Server exception: " + e.toString());
        e.printStackTrace();
    }
    }

    public int getCurrent_processor() {
        return current_processor;
    }

    public ArrayList<Buffer> getLocalBuffer() {
        return localBuffer;
    }

    public void send(Message message) throws RemoteException, NotBoundException, MalformedURLException {
        int destination = message.getProcessorid();

        SES_RMI stub = (SES_RMI) Naming.lookup("SES-"+destination);
//        System.err.println(current_processor);
        System.err.println(message.getMessage() + " has been sent to "+ message.getProcessorid());
        processors[current_processor]++;
        message.setTimestamp(processors);
//        System.err.println(Arrays.toString(message.getTimestamp()));
        try {
            Thread.sleep(message.getDelay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stub.receive(message);
//        System.err.println(message.getProcessorid()+ " has recieved the message");
        boolean exist = false;
        for(Buffer i: localBuffer){
            if(i.getId()==destination){
                i.setTimestamp(processors);
                exist = true;
            }
        }

        if(!exist){
            Buffer firstInteraction =new Buffer(processors.length,destination);
            firstInteraction.setTimestamp(processors);
            localBuffer.add(firstInteraction);
        }
//        System.err.println(current_processor + " local buffer:" + localBuffer.toString());
    }

    @Override
    public void receive(Message message) throws RemoteException {
        System.err.println(message.toString());
        System.err.println("Timestamp: " + current_processor +" " +Arrays.toString(processors));

        boolean delivered = false;
        if(message.getBuffers().isEmpty()){
            System.err.println("Message from " + message.getMessage()+" has been delivered");
            deliver(message);
            deliverMessageBuffers();
            delivered=true;
        }
        else {
            for (Buffer i : message.getBuffers()) {
                if ((i.getId() == current_processor) && (i.compareTimestamp(processors))) {
                    System.err.println("Message from " + message.getMessage()+" has been delivered");
                    deliver(message);
                    deliverMessageBuffers();
                    delivered = true;
                }
            }
        }
        if(!delivered){
            messageBuffer.add(message);
        }
    }


    public void deliver(Message message){
//        System.out.println("Delivered: "+message.getMessage());
        for(Buffer i: message.getBuffers()){
            for(Buffer j: localBuffer){
                if(i.getId()==j.getId()){
                    int[] newVector = i.max(message.getTimestamp());
                    Buffer updatedBuffer = new Buffer(i.getId(), j.getTimestamp().length);
                    updatedBuffer.setTimestamp(newVector);
                    localBuffer.add(updatedBuffer);
                    localBuffer.remove(j);
                    break;
                }
            }
        }
        Buffer holder = new Buffer(processors.length, 0);
        holder.setTimestamp(message.getTimestamp());
        processors = holder.max(processors);
//        System.out.println(current_processor + " local buffer:" + localBuffer.toString());
    }

    private void deliverMessageBuffers(){
        for(Message i: messageBuffer){
            for(Buffer j: i.getBuffers()){
                if((j.getId()==current_processor)&&(j.compareTimestamp(processors))){
                    deliver(i);
                    messageBuffer.remove(i);
                    break;
                }
            }
        }
    }


}
