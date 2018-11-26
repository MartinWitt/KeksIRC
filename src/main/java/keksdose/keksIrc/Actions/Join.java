package keksdose.keksIrc.Actions;

import keksdose.keksIrc.Helper.Strings;

public class Join {

    public static String JoinAction(String channel) {
        return "JOIN " + channel + Strings.newLine.getContent();
    }
}