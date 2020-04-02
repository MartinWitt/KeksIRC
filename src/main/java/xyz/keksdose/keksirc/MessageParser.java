package xyz.keksdose.keksirc;

import java.util.Optional;

import xyz.keksdose.keksirc.message.Message;
import xyz.keksdose.keksirc.network.SocketHandler;
import xyz.keksdose.keksirc.parser.Parser;
import xyz.keksdose.keksirc.parser.PingMessageParser;
import xyz.keksdose.keksirc.parser.PrivMessageParser;

public class MessageParser {
    private Parser privParser;
    private Parser pingParser;

    public MessageParser(SocketHandler s) {
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
