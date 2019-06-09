package keksdose.keksIrc;

import java.util.Optional;

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

    public Optional<Message> parserMessage(String message) {

        if (message.startsWith("PING")) {
            return Optional.of(pingParser.parse(message));

        }
        if (message.contains("PRIVMSG")) {
            return Optional.of(privParser.parse(message));
        }
        return Optional.empty();
    }

}