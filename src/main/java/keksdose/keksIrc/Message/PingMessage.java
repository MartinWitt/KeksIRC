package keksdose.keksIrc.Message;

import keksdose.keksIrc.Helper.Strings;

public class PingMessage implements Message {
    private String hostname;
    private String channel;
    private String content;
    private String type = "PING";

    public PingMessage(String hostname, String channel, String content) {
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

    @Override
    public String answer(String content) {
        return this.content + Strings.newLine.getContent();
    }

}