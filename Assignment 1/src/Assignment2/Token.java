package Assignment2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class Token {
    private int[] sequenceNumberList;
    private Queue<Integer> componentOrder;

    public Token(int components) {
        this.sequenceNumberList = new int[components];
        this.componentOrder = new ArrayDeque<>(components);
    }

    public void updateSequenceNumberList(int index, int newValue) {
        sequenceNumberList[index] = newValue;
    }

    public int getSequenceNumber(int index) {
        return sequenceNumberList[index];
    }

    public void addComponentToQueue(int componentId) {
        componentOrder.add(componentId);
    }

    public int getNextComponent() {
        return componentOrder.poll();
    }

    @Override
    public String toString() {
        return "Token{" +
                "sequenceNumberList=" + Arrays.toString(sequenceNumberList) +
                ", componentOrder=" + componentOrder +
                '}';
    }
}
