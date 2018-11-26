package keksdose.keksIrc.Message;

import keksdose.keksIrc.Helper.Strings;

public class PrivMessage implements Message {

    private String hostname;
    private String channel;
    private String content;
    private String type = "PRIVMSG";

    public PrivMessage(String hostname, String channel, String content) {
        super();
        this.hostname = hostname;
        this.channel = channel;
        this.content = content;
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
        return "PRIVMSG " + this.getChannel() + " :" + Strings.zwnbsp.getContent() + content
                + Strings.newLine.getContent();
    }

}