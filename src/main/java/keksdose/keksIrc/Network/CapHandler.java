package keksdose.keksIrc.Network;

public class CapHandler {

    long timeLastMessage = 0;

    public void waitForNext() {
        while ((System.currentTimeMillis() - timeLastMessage) < 200)
            ;
        timeLastMessage = System.currentTimeMillis();
    }
}
