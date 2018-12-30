package keksdose.keksIrc.Modell;

import java.util.concurrent.ArrayBlockingQueue;

public class Channel {

    private ArrayBlockingQueue<String> messages;

    public Channel(String name) {
        messages = new ArrayBlockingQueue<>(100);
    }

    public boolean hasNext() {
        return !messages.isEmpty();
    }

    public String getNextMessage() {
        return messages.poll();
    }

}