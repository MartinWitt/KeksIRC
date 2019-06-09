package keksdose.keksIrc.Network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.Executors;

public class SocketHandler {
    private ArrayBlockingQueue<String> messages;
    private BufferedWriter out;
    private BufferedReader in;
    private CapHandler cap;

    public SocketHandler(Connector c) {
        messages = new ArrayBlockingQueue<>(100);
        try {
            out = new BufferedWriter(new OutputStreamWriter(c.getOut(), "UTF-8"));
            in = new BufferedReader(new InputStreamReader(c.getIn(), "UTF-8"));

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        read();
    }

    public SocketHandler(Connector c, CapHandler cap) {
        messages = new ArrayBlockingQueue<>(100);
        this.cap = cap;
        try {
            out = new BufferedWriter(new OutputStreamWriter(c.getOut(), "UTF-8"));
            in = new BufferedReader(new InputStreamReader(c.getIn(), "UTF-8"));

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        read();
    }

    private void read() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {

            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        String var = in.readLine();
                        messages.put(var);
                    } catch (IOException | InterruptedException e) {
                        Thread.currentThread().interrupt();
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public void sendMessage(String message) {
        if (cap != null) {
            cap.waitForNext();
        }
        try {
            out.write(message);
            out.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean hasNext() {
        return !messages.isEmpty();
    }

    public String getNextMessage() {
        try {
            return messages.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        return "";

    }
}
