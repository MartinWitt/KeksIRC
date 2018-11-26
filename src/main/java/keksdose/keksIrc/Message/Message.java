package keksdose.keksIrc.Message;

public interface Message {
    public abstract String getHostName();

    public abstract String getChannel();

    public abstract String getContent();

    public abstract String getType();

    public abstract String answer(String Content);

}