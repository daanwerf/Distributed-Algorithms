import java.io.Serializable;
import java.util.Arrays;

public class Buffer implements Serializable {
    private int[] timestamp;
    private int id;

    public Buffer(int processors, int id) {
        this.timestamp = new int[processors];
        this.id = id;
    }

    public int[] getTimestamp() {
        return timestamp;
    }

    public boolean compareTimestamp(int[] requirement){
        for (int i= 0; i<requirement.length; i++){
            if(!(timestamp[i]<= requirement[i])){
                return false;
            }
        }
        return true;
    }

    public int[] max(int[] timestamp){
        for(int i=0; i<timestamp.length;i++){
            if(timestamp[i]<this.timestamp[i]){
                timestamp[i]=this.timestamp[i];
            }
        }
        return timestamp;
    }

    public void setTimestamp(int[] timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Buffer{" +
                "timestamp=" + Arrays.toString(timestamp) +
                ", id=" + id +
                '}';
    }

}
