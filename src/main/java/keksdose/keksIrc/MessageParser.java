package keksdose.keksIrc;

import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Network.SocketHandler;
import keksdose.keksIrc.Parser.Parser;
import keksdose.keksIrc.Parser.PingMessageParser;
import keksdose.keksIrc.Parser.PrivMessageParser;

public class MessageParser {
    private Parser privParser;
    private Parser pingParser;
    private SocketHandler s;

    public MessageParser(SocketHandler s) {
        this.s = s;
        pingParser = new PingMessageParser();
        privParser = new PrivMessageParser(s);
    }

    public Message parserMessage(String message) {

        if (message.startsWith("PING")) {
            return pingParser.parse(message);

        }
        if (message.contains("PRIVMSG")) {
            Message m = privParser.parse(message);
            return m;
        }
        return null;
    }

}