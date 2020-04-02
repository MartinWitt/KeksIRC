package xyz.keksdose.keksirc.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xyz.keksdose.keksirc.helper.Strings;
import xyz.keksdose.keksirc.network.SocketHandler;

public class PrivMessage implements Message {

    private String nick;
    private String hostName;
    private String channel;
    private String content;

    private String type = "PRIVMSG";
    private SocketHandler handler;

    public PrivMessage(String hostname, String channel, String content, SocketHandler handler,
            String nick) {
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



    public String answer(String content) {
        String[] splitted = content.split("\n");
        for (String var : splitted) {
            List<String> parts = new ArrayList<>();

            int length = var.length();
            for (int i = 0; i < length; i += 510) {
                parts.add(var.substring(i, Math.min(length, i + 510)));
            }
            for (String value : parts) {
                String s = ("PRIVMSG " + this.getChannel() + " :" + value
                        + Strings.newLine.getContent());
                try {
                    handler.sendMessage(s);
                } catch (IOException e) {
                    System.err.println("could not send message");
                }
            }

        }
        return "";
    }

    public String answerNoPrefix(String content) {
        String[] splitted = content.split("\n");
        for (String var : splitted) {
            List<String> parts = new ArrayList<>();

            int length = var.length();
            for (int i = 0; i < length; i += 450) {
                parts.add(var.substring(i, Math.min(length, i + 450)));
            }
            for (String value : parts) {
                String s = ("PRIVMSG " + this.getChannel() + " :" + value
                        + Strings.newLine.getContent());
                try {
                    handler.sendMessage(s);
                } catch (IOException e) {
                    System.err.println("could not send message");
                }
            }
        }
        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "PrivMessage [channel=" + channel + ", content=" + content + ", hostName=" + hostName
                + ", nick=" + nick + "]";
    }
}
