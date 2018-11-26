package keksdose.keksIrc.Helper;

public enum Strings {

    newLine("\r\n"), zwnbsp('\u200B');

    private String content;

    private Strings(String content) {
        this.content = content;
    }

    private Strings(char content) {
        this.content = String.valueOf(content);
    }

    public String getContent() {
        return this.content;
    }
}
