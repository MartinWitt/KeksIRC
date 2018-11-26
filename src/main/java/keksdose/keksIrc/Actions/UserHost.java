package keksdose.keksIrc.Actions;

import keksdose.keksIrc.Helper.Strings;

public class UserHost {

    public static String UserHost(String user, String host) {
        return "USER " + user + " " + "0 * : " + host + Strings.newLine.getContent();
    }
}