package xyz.keksdose.keksirc.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.Executors;
import xyz.keksdose.keksirc.network.caphandler.CapHandler;
import xyz.keksdose.keksirc.network.caphandler.DefaultCapHandler;

public class SocketHandler {

  private static final Charset UTF8_ENCODING = Charset.forName("UTF-8");
  private ArrayBlockingQueue<String> messages;
  private BufferedWriter out;
  private BufferedReader in;
  private CapHandler cap;

  public static SocketHandler socketHandlerWithCapHandler(Connector c, CapHandler cap) {
    return new SocketHandler(c, cap);
  }

  public static SocketHandler socketHandlerWithoutCapHandler(Connector c) {
    return new SocketHandler(c, new DefaultCapHandler());
  }


  private SocketHandler(Connector c, CapHandler cap) {
    messages = new ArrayBlockingQueue<>(100);
    this.cap = cap;
    out = new BufferedWriter(new OutputStreamWriter(c.getOut(), UTF8_ENCODING));
    in = new BufferedReader(new InputStreamReader(c.getIn(), UTF8_ENCODING));
  }

  public void startMessageReading() {
    read();
  }

  private void read() {
    Executors.newSingleThreadExecutor().submit(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        try {
          messages.put(in.readLine());
        } catch (IOException | InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
  }

  public void sendMessage(String message) throws IOException {
    cap.waitMessageCap();
    try {
      out.write(message);
      out.flush();
    } catch (IOException e) {
      // cant send message maybe rethrow?
      throw e;
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
      return "";
    }
  }
}
