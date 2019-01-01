package keksdose.keksIrc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
    private List<String> channel = new LinkedList<>();
    private String prefix = Strings.zwnbsp.getContent();
    private ArrayBlockingQueue<Message> list;

    public IRCStart(ArrayBlockingQueue<Message> container) {
        this.list = container;
    }

    public void start() throws IOException {
        CapHandler cap = new CapHandler();
        int port = 7000; // 6667 non ssl 7000 ssl
        String host = "chat.freenode.net";
        Connector c = new Connector(useSSL);
        User u = new User(hostname, username, nickname);
        c.connect(host, port);
        System.out.println("connected with socket");
        SocketHandler s = new SocketHandler(c, cap);
        System.out.println("created streams");
        MessageParser parser = new MessageParser(s);
        s.sendMessage(Nick.NickAction(u.getNickname()));
        s.sendMessage(UserHost.UserHost(u.getHostname(), u.getUsername()));
        for (String var : channel) {
            s.sendMessage(Join.JoinAction(var));
        }
        // TODO MULTICHANNEL
        while (true) {
            Message m = parser.parserMessage(s.getNextMessage());
            if (m == null) {
                continue;
            }
            if (m.getType().equals("PING")) {
                s.sendMessage(m.answer(""));
                continue;
            }
            list.add(m);
            if (m.getType().equals("PRIVMSG")) {
                if (m.getContent().startsWith("#echo") && m.getContent().length() > 5) {
                    if (useCapHandler) {
                        cap.waitForNext();
                    }
                    s.sendMessage(m.answer(m.getContent().substring(6)));
                }
            }

        }
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

    public void addChannel(String channel) {
        this.channel.add(channel);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setCapHandler(boolean var) {
        useCapHandler = var;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof IRCStart)) {
            return false;
        }
        IRCStart iRCStart = (IRCStart) o;
        return useSSL == iRCStart.useSSL && usePrefix == iRCStart.usePrefix && useCapHandler == iRCStart.useCapHandler
                && port == iRCStart.port && Objects.equals(hostname, iRCStart.hostname)
                && Objects.equals(username, iRCStart.username) && Objects.equals(nickname, iRCStart.nickname)
                && Objects.equals(prefix, iRCStart.prefix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(useSSL, usePrefix, useCapHandler, port, hostname, username, nickname, prefix);
    }

    @Override
    public String toString() {
        return "{" + " useSSL='" + isUseSSL() + "'" + ", usePrefix='" + isUsePrefix() + "'" + ", useCapHandler='"
                + isUseCapHandler() + "'" + ", port='" + getPort() + "'" + ", hostname='" + getHostname() + "'"
                + ", username='" + getUsername() + "'" + ", nickname='" + getNickname() + "'" + "'" + ", prefix='"
                + getPrefix() + "'" + "}";
    }

}