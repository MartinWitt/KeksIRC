package xyz.keksdose.keksirc;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

import xyz.keksdose.keksirc.message.Message;

public class Main {

    public static void main(String[] args) {
        ArrayBlockingQueue<Message> list = new ArrayBlockingQueue<>(100);
        var start = new IRCStart(list);
        start.setUseCapHandler(true);
        IRCStart.setNickname("FWKIB|Test");
        start.addChannel("##fwkib");

        // start.addChannel("#kitinfo");
        try {
            start.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }
    }

}
