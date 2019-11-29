import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Message implements Serializable {

    private int processorid;
    private String message;
    private ArrayList<Buffer> buffers;
    private int[] timestamp;
    private int delay;

    public Message(int processorid, String message, ArrayList<Buffer> buffer, int[] timestamp, int delay) {
        this.processorid = processorid;
        this.message = message;
        this.buffers = buffer;
        this.timestamp = timestamp;
        this.delay = delay;
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

    public int[] getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int[] timestamp) {
        this.timestamp = timestamp;
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Message{" +
                "processorid=" + processorid +
                ", message='" + message + '\'' +
                ", buffers=" + buffers +
                ", timestamp=" + Arrays.toString(timestamp) +
                ", delay=" + delay +
                '}';
    }
}
