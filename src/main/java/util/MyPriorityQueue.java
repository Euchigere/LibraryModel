package util;

import model.User;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Custom priority queue implementation
 */

public class MyPriorityQueue {
    // Array implemented as queue to save entries
    List<Map.Entry<User, String>> priorityQueue = new ArrayList<>();

    public boolean add(Map.Entry<User, String> simpleEntry) {
        // Ensures only unique entries are added
        if (priorityQueue.contains(simpleEntry)) {
            return false;
        }

        // Loops through queue an insert entry at the appropriate position
        for (int i = 0; i < priorityQueue.size(); i++) {
            if (simpleEntry.getValue().equals(priorityQueue.get(i).getValue())
                && simpleEntry.getKey().getRanking() > priorityQueue.get(i).getKey().getRanking()) {
                priorityQueue.add(i, simpleEntry);
                return true;
            }
        }
        // Adds entry at the back of the queue if otherwise
        priorityQueue.add(simpleEntry);
        return true;
    }

    // checks if queue is empty
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    // remove and return entry at the head of queue
    public Map.Entry<User, String> remove() {
        return priorityQueue.remove(0);
    }

    // returns entry at the head of the queue
    public Map.Entry<User, String> peek() {
        return priorityQueue.get(0);
    }

    // clears all the value in queue
    public void clear() {
        priorityQueue.clear();
    }

    // return the size of the queue
    public int size() {
        return priorityQueue.size();
    }
}
