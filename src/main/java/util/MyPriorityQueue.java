package util;

import model.User;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class MyPriorityQueue {
    List<Map.Entry<User, String>> priorityQueue = new ArrayList<>();

    public boolean add(Map.Entry<User, String> simpleEntry) {
        if (priorityQueue.contains(simpleEntry)) {
            return false;
        }

        for (int i = 0; i < priorityQueue.size(); i++) {
            if (simpleEntry.getValue().equals(priorityQueue.get(i).getValue())
                && simpleEntry.getKey().getRanking() > priorityQueue.get(i).getKey().getRanking()) {
                priorityQueue.add(i, simpleEntry);
                return true;
            }
        }
        priorityQueue.add(simpleEntry);
        return true;
    }

    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }

    public Map.Entry<User, String> remove() {
        return priorityQueue.remove(0);
    }

    public Map.Entry<User, String> peek() {
        return priorityQueue.get(0);
    }

    public void clear() {
        priorityQueue.clear();
    }

    public int size() {
        return priorityQueue.size();
    }
}
