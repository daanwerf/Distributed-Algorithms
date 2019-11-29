import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

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

        try{
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("SES-"+current_processor, this);
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
        processors[current_processor] = processors[current_processor]++;
        stub.receive(message);
        boolean exist = false;
        for(Buffer i: localBuffer){
            if(i.getId()==destination){
                i.setTimestamp(processors);
                exist = true;
            }
        }

        if(!exist){
            Buffer firstInteraction =new Buffer(destination, processors.length);
            firstInteraction.setTimestamp(processors);
            localBuffer.add(firstInteraction);
        }
    }

    @Override
    public void receive(Message message) throws RemoteException {
        boolean delivered = false;
        for(Buffer i: message.getBuffers()){
            if((i.getId()==current_processor)&&(i.compareTimestamp(processors))){
                deliver(message);
                deliverMessageBuffers();
                delivered=true;
            }
        }
        if(!delivered){
            messageBuffer.add(message);
        }
    }


    public void deliver(Message message){
        System.out.println("Delivered: "+message.getMessage());
        for(Buffer i: message.getBuffers()){
            for(Buffer j: localBuffer){
                if(i.getId()==j.getId()){
                    int[] newVector = i.max(j.getTimestamp());
                    Buffer updatedBuffer = new Buffer(i.getId(), j.getTimestamp().length);
                    updatedBuffer.setTimestamp(newVector);
                    localBuffer.add(updatedBuffer);
                    localBuffer.remove(j);
                    break;
                }
            }
        }
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
