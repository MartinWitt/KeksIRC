package keksdose.keksIrc.Message;

import keksdose.keksIrc.Helper.Strings;
import keksdose.keksIrc.Network.SocketHandler;

public class PrivMessage implements Message {

    private String hostname;
    private String channel;
    private String content;
    private String type = "PRIVMSG";
    private SocketHandler handler;

    public PrivMessage(String hostname, String channel, String content, SocketHandler handler) {
        this.hostname = hostname;
        this.channel = channel;
        this.content = content;
        this.handler = handler;
    }

    @Override
    public String getHostName() {
        return hostname;
    }

    @Override
    public String getChannel() {
        return channel;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getType() {
        return type;
    }

    public String answer(String content) {
        String s = ("PRIVMSG " + this.getChannel() + " :" + Strings.zwnbsp.getContent()

                + content + Strings.newLine.getContent());
        handler.sendMessage(s);

        return s;
    }

}