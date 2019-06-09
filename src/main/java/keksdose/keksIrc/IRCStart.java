package keksdose.keksIrc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

import keksdose.keksIrc.Actions.Join;
import keksdose.keksIrc.Actions.Nick;
import keksdose.keksIrc.Actions.UserHost;
import keksdose.keksIrc.Helper.Strings;
import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Modell.User;
import keksdose.keksIrc.Network.CapHandler;
import keksdose.keksIrc.Network.Connector;
import keksdose.keksIrc.Network.SocketHandler;

public class IRCStart {

    private boolean useSSL = true;
    private boolean usePrefix = false;
    private boolean useCapHandler = true;
    private int port = 7000;
    private String hostname = "FWKIB";
    private String username = "FWKIB";
    private String nickname = "FWKIB";
    private String host = "chat.freenode.net";
    private List<String> channel = new LinkedList<>();
    private String prefix = Strings.zwnbsp.getContent();
    private ArrayBlockingQueue<Message> list;

    public IRCStart(ArrayBlockingQueue<Message> container) {
        this.list = container;
    }

    public void start() throws IOException {
        CapHandler cap = new CapHandler();
        Connector c = new Connector(useSSL);
        User u = new User(hostname, username, nickname);
        c.connect(host, port);
        System.out.println("connected with socket");
        SocketHandler s = new SocketHandler(c, cap);
        System.out.println("created streams");
        MessageParser parser = new MessageParser(s);
        loginToServer(u, s);
        for (String var : channel) {
            s.sendMessage(Join.JoinAction(var));
        }
        // TODO MULTICHANNEL
        for (;;) {
            Optional<Message> m = parser.parserMessage(s.getNextMessage());
            if (!m.isPresent()) {
                continue;
            }
            Message message = m.get();
            if (message.getType().equals("PING")) {
                s.sendMessage(message.answer(""));
                continue;
            }
            list.add(message);

        }
    }

    private void loginToServer(User u, SocketHandler s) {
        s.sendMessage(Nick.NickAction(u.getNickname()));
        s.sendMessage(UserHost.UserHost(u.getHostname(), u.getUsername()));
    }

    public boolean isUseSSL() {
        return this.useSSL;
    }

    public boolean getUseSSL() {
        return this.useSSL;
    }

    public void setUseSSL(boolean useSSL) {
        this.useSSL = useSSL;
    }

    public boolean isUsePrefix() {
        return this.usePrefix;
    }

    public boolean getUsePrefix() {
        return this.usePrefix;
    }

    public void setUsePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
    }

    public boolean isUseCapHandler() {
        return this.useCapHandler;
    }

    public boolean getUseCapHandler() {
        return this.useCapHandler;
    }

    public void setUseCapHandler(boolean useCapHandler) {
        this.useCapHandler = useCapHandler;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void addChannel(String string) {
        channel.add(string);
    }

}