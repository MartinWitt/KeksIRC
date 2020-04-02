package xyz.keksdose.keksirc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import xyz.keksdose.keksirc.action.Actions;
import xyz.keksdose.keksirc.helper.Strings;
import xyz.keksdose.keksirc.message.Message;
import xyz.keksdose.keksirc.modell.User;
import xyz.keksdose.keksirc.network.Connector;
import xyz.keksdose.keksirc.network.SocketHandler;
import xyz.keksdose.keksirc.network.caphandler.CapHandler;
import xyz.keksdose.keksirc.network.caphandler.TimeCapHandler;

public class IRCStart {

    private boolean useSSL = true;
    private boolean usePrefix = false;
    private boolean useCapHandler = true;
    private int port = 7000;
    private static String hostname = "FWKIB";
    private static String username = "FWKIB";
    private static String nickname = "FWKIB";
    private String host = "chat.freenode.net";
    private List<String> channel = new LinkedList<>();
    private ArrayBlockingQueue<Message> list;

    public IRCStart(ArrayBlockingQueue<Message> container) {
        this.list = container;
    }

    public void start() throws IOException {
        CapHandler cap = new TimeCapHandler();
        Connector c = Connector.connectorWithSSL();
        User u = new User(hostname, username, nickname);
        c.connect(host, port);
        System.out.println("connected with socket");
        SocketHandler s = SocketHandler.socketHandlerWithCapHandler(c, cap);
        s.startMessageReading();
        System.out.println("created streams");
        MessageParser parser = new MessageParser(s);
        loginToServer(u, s);
        for (String var : channel) {
            s.sendMessage(Actions.joinAction(var));
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
            // message.answer(Character.toString(29) + "die ist ein test" + Character.toString(29));
            list.add(message);

        }
    }

    private void loginToServer(User u, SocketHandler s) {
        try {
            s.sendMessage(Actions.nickAction(u.getNickname()));
            s.sendMessage(Actions.userHostAction(u.getHostname(), u.getUsername()));
        } catch (IOException e) {
            System.err.println("could not connect to given socket with given user");
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

    /**
     * @return the hostname
     */
    public static String getHostname() {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public static void setHostname(String hostname) {
        IRCStart.hostname = hostname;
    }

    /**
     * @return the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public static void setUsername(String username) {
        IRCStart.username = username;
    }

    /**
     * @return the nickname
     */
    public static String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public static void setNickname(String nickname) {
        IRCStart.nickname = nickname;
    }

    public void addChannel(String string) {
        channel.add(string);
    }

    public static boolean isUserName(String nick) {
        return nickname.equals(nick);
    }
}
