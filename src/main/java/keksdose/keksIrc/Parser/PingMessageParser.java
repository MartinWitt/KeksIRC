package keksdose.keksIrc.Parser;

import keksdose.keksIrc.Message.Message;
import keksdose.keksIrc.Message.PingMessage;

public class PingMessageParser implements Parser {

    @Override
    public Message parse(String input) {
        return new PingMessage("", "", input.substring(5));
    }

}
