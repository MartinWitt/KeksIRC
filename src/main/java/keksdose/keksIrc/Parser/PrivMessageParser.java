package keksdose.keksIrc.Parser;

import java.util.Arrays;

import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Message.PrivMessage;
import keksdose.keksIrc.Network.SocketHandler;;

public class PrivMessageParser implements Parser {
    private SocketHandler handler;

    public PrivMessageParser(SocketHandler s) {
        this.handler = s;
    }

    @Override
    public Message parse(String input) {
        String[] result = input.split(" ", 4);
        if (result.length < 4) {
            return null;
        }
        String content = result[3].substring(1);
        String channel = result[2]; // eigentlich auch NUtzer bei Whisper
        String user = result[0].split("!", 2)[0].replaceFirst(":", "");
        String hostname = result[0].split("@", 2).length == 2 ? result[0].split("@", 2)[1] : result[0];
        return new PrivMessage(hostname, channel, content, handler, user);
        // todo: aufteilen des splits und dann nachricht erstellen.
    }

}
