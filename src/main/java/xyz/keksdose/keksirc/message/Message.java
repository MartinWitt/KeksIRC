package xyz.keksdose.keksirc.message;

public interface Message {
    public abstract String getHostName();

    public abstract String getChannel();

    public abstract String getContent();

    public abstract String getType();

    public abstract String answer(String Content);

    public abstract String answerNoPrefix(String content);

    public default String getNick() {
        return "";
    }

}
