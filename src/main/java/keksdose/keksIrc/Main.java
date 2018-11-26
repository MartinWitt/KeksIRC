package keksdose.keksIrc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import keksdose.keksIrc.Actions.Join;
import keksdose.keksIrc.Actions.Nick;
import keksdose.keksIrc.Actions.UserHost;
import keksdose.keksIrc.Helper.Strings;
import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Modell.User;
import keksdose.keksIrc.Network.Connector;
import keksdose.keksIrc.Parser.Parser;
import keksdose.keksIrc.Parser.PingMessageParser;
import keksdose.keksIrc.Parser.PrivMessageParser;

public class Main {
    private static String channel = "#kitinfo-botnet";
    private static Parser privParser = new PrivMessageParser();
    private static Parser pingParser = new PingMessageParser();

    public static void main(String[] args) {
        try {
            int port = 7000; // 6667 non ssl 7000 ssl
            String host = "chat.freenode.net";
            Connector c = new Connector(true);
            User u = new User("Sleepdose", "testkeks", "testkeks");
            c.connect(host, port);
            System.out.println("connected with socket");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(c.getOut(), "UTF-8"));
            BufferedReader in = new BufferedReader(new InputStreamReader(c.getIn(), "UTF-8"));
            System.out.println("created streams");
            out.write(Nick.NickAction(u.getNickname()));
            out.flush();
            test(in, out);
            out.write(UserHost.UserHost(u.getHostname(), u.getUsername()));
            out.flush();
            test(in, out);
            out.write(Join.JoinAction(channel));
            out.flush();

            while (true) {
                test(in, out);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("fertig");
    }

    public static void test(BufferedReader in, BufferedWriter out) throws IOException {
        while (true) {
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
