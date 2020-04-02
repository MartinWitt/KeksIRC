package xyz.keksdose.keksirc.parser;

import xyz.keksdose.keksirc.message.Message;

public interface Parser {

    public Message parse(String input);
}
