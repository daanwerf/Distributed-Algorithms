import java.util.ArrayList;

public class Message {

    private int processorid;
    private String message;
    private ArrayList<Buffer> buffers;

    public Message(int processorid, String message, ArrayList<Buffer> buffer) {
        this.processorid = processorid;
        this.message = message;
        this.buffers = buffer;
    }

    public int getProcessorid() {
        return processorid;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Buffer> getBuffers() {
        return buffers;
    }
}
