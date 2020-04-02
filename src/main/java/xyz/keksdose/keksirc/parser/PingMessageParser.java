package xyz.keksdose.keksirc.parser;

import xyz.keksdose.keksirc.message.Message;
import xyz.keksdose.keksirc.message.PingMessage;

public class PingMessageParser implements Parser {

    @Override
    public Message parse(String input) {
        return new PingMessage("", "", input.substring(5));
    }

}
