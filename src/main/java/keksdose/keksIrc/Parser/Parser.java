package keksdose.keksIrc.Parser;

import keksdose.keksIrc.Message.Message;

public interface Parser {

    public Message parse(String input);
}
