package keksdose.keksIrc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import keksdose.keksIrc.Helper.Strings;
import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Parser.Parser;
import keksdose.keksIrc.Parser.PingMessageParser;
import keksdose.keksIrc.Parser.PrivMessageParser;

public class Main {
    private static String channel = "#kitinfo-botnet";
    private static Parser privParser = new PrivMessageParser();
    private static Parser pingParser = new PingMessageParser();

    public static void main(String[] args) {
        try {
            int port = 6667;
            String host = "chat.freenode.net";
            Socket socket = new Socket(host, port);
            System.out.println("connected with socket");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

            out.write("NICK testKeks \r\n");
            out.flush();
            test(in, out);
            out.write("USER testKeks 0 * : SleepKeks \r\n");
            out.flush();
            test(in, out);
            out.write("JOIN " + channel + "\r\n");
            out.flush();

            while (true) {
                if (in.ready()) {
                    test(in, out);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("fertig");
    }

    public static void test(BufferedReader in, BufferedWriter out) throws IOException {
        while (true) {
            if (in.ready()) {
                String var = in.readLine();
                if (var.startsWith("PING")) {
                    Message m = pingParser.parse(var);
                    out.write(m.answer(""));
                    out.flush();
                }
                if (var.contains("PRIVMSG") && var.contains(channel)) {
                    Message m = privParser.parse(var);
                    System.out.println("sende:  " + "PRIVMSG " + m.getChannel() + " :" + Strings.zwnbsp.getContent()
                            + m.getContent() + Strings.newLine.getContent());
                    out.write(m.answer("ich bin ein toller echo-bot:" + m.getContent()));
                    out.flush();
                }
                System.out.println(var);
                return;
            }
        }
    }
}
