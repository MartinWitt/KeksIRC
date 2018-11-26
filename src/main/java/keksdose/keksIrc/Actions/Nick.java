package keksdose.keksIrc.Actions;

import keksdose.keksIrc.Helper.Strings;

public class Nick {

    public static String NickAction(String nickname) {
        return "NICK " + nickname + Strings.newLine.getContent();
    }
}