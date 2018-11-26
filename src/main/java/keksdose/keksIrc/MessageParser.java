package keksdose.keksIrc;

import keksdose.keksIrc.Helper.Strings;
import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Parser.Parser;
import keksdose.keksIrc.Parser.PingMessageParser;
import keksdose.keksIrc.Parser.PrivMessageParser;

public class MessageParser {
    private Parser privParser = new PrivMessageParser();
    private Parser pingParser = new PingMessageParser();

    public Message parserMessage(String message) {

        if (message.startsWith("PING")) {
            return pingParser.parse(message);

        }
        if (message.contains("PRIVMSG")) {
            Message m = privParser.parse(message);
            System.out.println("sende:  " + "PRIVMSG " + m.getChannel() + " :" + Strings.zwnbsp.getContent()
                    + m.getContent() + Strings.newLine.getContent());
            return m;
        }
        return null;
    }

}