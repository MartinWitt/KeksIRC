package xyz.keksdose.keksirc.action;

import xyz.keksdose.keksirc.helper.Strings;

public class Actions {
  private Actions() {
    // private because static methods only
  }

  public static String joinAction(String channel) {
    return "JOIN " + channel + Strings.newLine.getContent();
  }

  public static String nickAction(String nickname) {
    return "NICK " + nickname + Strings.newLine.getContent();
  }

  public static String userHostAction(String user, String host) {
    return "USER " + user + " " + "0 * : " + host + Strings.newLine.getContent();
  }
}
