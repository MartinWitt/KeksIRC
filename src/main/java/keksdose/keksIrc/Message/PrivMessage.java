package keksdose.keksIrc.Message;

import java.util.ArrayList;
import java.util.List;

import keksdose.keksIrc.Helper.Strings;
import keksdose.keksIrc.Network.SocketHandler;

public class PrivMessage implements Message {

    private String nick;
    private String hostName;
    private String ip; // TODO: das ist locker was anders
    private String channel;
    private String content;

    private String type = "PRIVMSG";
    private SocketHandler handler;

    public PrivMessage(String hostname, String channel, String content, SocketHandler handler, String nick) {
        this.hostName = hostname;
        this.channel = channel;
        this.content = content;
        this.handler = handler;
        this.nick = nick;
        System.out.println(this.toString());

    }

    @Override
    public String getHostName() {
        return hostName;
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
    public String getNick() {
        return this.nick;
    }

    public String getIp() {
        return this.ip;
    }

    @Override
    public String toString() {
        return "{" + " nick='" + getNick() + "'" + ", hostname='" + getHostName() + "'" + ", ip='" + getIp() + "'"
                + ", channel='" + getChannel() + "'" + ", content='" + getContent() + "'" + ", type='" + getType() + "'"
                + "'" + "}";
    }

    public String answer(String content) {
        String[] splitted = content.split("\n");
        for (String var : splitted) {
            List<String> parts = new ArrayList<>();

            int length = var.length();
            for (int i = 0; i < length; i += 510) {
                parts.add(var.substring(i, Math.min(length, i + 510)));
            }
            for (String value : parts) {
                String s = ("PRIVMSG " + this.getChannel() + " :" + value + Strings.newLine.getContent());
                handler.sendMessage(s);
            }

        }
        return "";
    }

    public String answerNoPrefix(String content) {
        String[] splitted = content.split("\n");
        for (String var : splitted) {
            List<String> parts = new ArrayList<>();

            int length = var.length();
            for (int i = 0; i < length; i += 510) {
                parts.add(var.substring(i, Math.min(length, i + 510)));
            }
            for (String value : parts) {
                String s = ("PRIVMSG " + this.getChannel() + " :" + value + Strings.newLine.getContent());
                handler.sendMessage(s);
            }

        }
        return "";
    }
}