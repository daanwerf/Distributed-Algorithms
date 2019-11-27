package Assignment2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Token {
    private int[] LN;
    private Queue<Integer> queue;

    public Token(int components) {
        this.LN = new int[components];
        this.queue = new ArrayDeque<>(components);
    }

    public void updateLN(int index, int newValue) {
        LN[index] = newValue;
    }

    public int getFromLN(int index) {
        return LN[index];
    }

    public Queue<Integer> getQueue() {
        return this.queue;
    }

    public void addComponentToQueue(int componentId) {
        queue.add(componentId);
    }

    public int getNextComponent() {
        return queue.poll();
    }

    @Override
    public String toString() {
        return "Token{" +
                "LN=" + Arrays.toString(LN) +
                ", queue=" + queue +
                '}';
    }
}
