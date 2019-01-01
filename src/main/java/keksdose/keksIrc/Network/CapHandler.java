package keksdose.keksIrc.Network;

import java.util.concurrent.TimeUnit;

public class CapHandler {
    public CapHandler() {
        timeLastMessage = System.currentTimeMillis();
    }

    long timeLastMessage = 0;

    public void waitForNext() {
        try {
            TimeUnit.MILLISECONDS.sleep(timeLastMessage + 200 - System.currentTimeMillis());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        timeLastMessage = System.currentTimeMillis();
    }
}
