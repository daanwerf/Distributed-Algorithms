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

    public int[] getLN() {
        return LN;
    }

    public Queue<Integer> getQueue() {
        return queue;
    }

    @Override
    public String toString() {
        return "Token{" +
                "LN=" + Arrays.toString(LN) +
                ", queue=" + queue +
                '}';
    }
}
