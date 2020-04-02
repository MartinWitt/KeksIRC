package xyz.keksdose.keksirc.parser;


import xyz.keksdose.keksirc.IRCStart;
import xyz.keksdose.keksirc.message.Message;
import xyz.keksdose.keksirc.message.PrivMessage;
import xyz.keksdose.keksirc.network.SocketHandler;;

public class PrivMessageParser implements Parser {
    private SocketHandler handler;

    public PrivMessageParser(SocketHandler s) {
        this.handler = s;
    }

    @Override
    public Message parse(String input) {
        System.out.println(input);
        String[] result = input.split(" ", 4);
        if (result.length < 4) {
            return null;
        }
        String content = result[3].substring(1);
        String channel = result[2]; // eigentlich auch NUtzer bei Whisper
        String user = result[0].split("!", 2)[0].replaceFirst(":", "");
        String hostname =
                result[0].split("@", 2).length == 2 ? result[0].split("@", 2)[1] : result[0];
        if (IRCStart.isUserName(channel)) {
            return new PrivMessage(hostname, user, content, handler, user);
        }
        return new PrivMessage(hostname, channel, content, handler, user);
        // todo: aufteilen des splits und dann nachricht erstellen.
    }

}
