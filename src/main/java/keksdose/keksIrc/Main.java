package keksdose.keksIrc;

import java.io.IOException;

import keksdose.keksIrc.Actions.Join;
import keksdose.keksIrc.Actions.Nick;
import keksdose.keksIrc.Actions.UserHost;
import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Modell.User;
import keksdose.keksIrc.Network.Connector;
import keksdose.keksIrc.Network.SocketHandler;

public class Main {
    private static String channel = "#kitinfo-botnet";

    public static void main(String[] args) {
        try {
            int port = 7000; // 6667 non ssl 7000 ssl
            String host = "chat.freenode.net";
            Connector c = new Connector(true);
            User u = new User("Sleepdose", "testkeks", "testkeks");
            c.connect(host, port);
            System.out.println("connected with socket");
            SocketHandler s = new SocketHandler(c);
            System.out.println("created streams");
            s.sendMessage(Nick.NickAction(u.getNickname()));
            s.sendMessage(UserHost.UserHost(u.getHostname(), u.getUsername()));
            s.sendMessage(Join.JoinAction(channel));
            MessageParser parser = new MessageParser();

            while (true) {
                if (s.hasNext()) {
                    Message m = parser.parserMessage(s.getNextMessage());
                    if (m == null) {
                        continue;
                    }
                    if (m.getType().equals("PING")) {
                        s.sendMessage(m.answer(""));
                    } else {
                        if (m.getContent().startsWith("#echo") && m.getContent().length() > 5) {
                            s.sendMessage(m.answer(m.getContent().substring(6)));
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("fertig");
    }

}
