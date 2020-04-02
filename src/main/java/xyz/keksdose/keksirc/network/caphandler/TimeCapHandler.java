package xyz.keksdose.keksirc.network.caphandler;

import java.util.concurrent.TimeUnit;

public class TimeCapHandler implements CapHandler {
    public TimeCapHandler() {
        timeLastMessage = System.currentTimeMillis();
    }

    private long timeLastMessage = 0;

    public void waitMessageCap() {
        try {
            TimeUnit.MILLISECONDS.sleep(timeLastMessage + 200 - System.currentTimeMillis());
        } catch (InterruptedException e) {
            // if interrupted just try again
            waitMessageCap();
        }
        timeLastMessage = System.currentTimeMillis();
    }
}
