package util;

import model.User;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Custom priority queue implementation
 */

public class MyPriorityQueue {
    // Array implemented as queue to save entries
    private List<Map.Entry<User, String>> priorityQueue = new ArrayList<>();

    public boolean add(Map.Entry<User, String> simpleEntry) {
        // Ensures only unique entries are added
        if (priorityQueue.contains(simpleEntry)) {
            return false;
        }

        // Loops through queue to find appropriate position
        int index = IntStream
                        .range(0, priorityQueue.size())
                        .filter(
                            i -> simpleEntry.getValue().equals(priorityQueue.get(i).getValue())
                                && simpleEntry.getKey().getRanking() > priorityQueue.get(i).getKey().getRanking()
                        )
                        .findFirst().orElse(priorityQueue.size());

        // Adds entry at the appropriate position
        priorityQueue.add(index, simpleEntry);
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

    public List<Map.Entry<User, String>> getQueue() {
        return priorityQueue;
    }
}
