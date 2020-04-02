package xyz.keksdose.keksirc.modell;

import java.util.Objects;

public class User {
    private String hostname;
    private String username;
    private String nickname;

    public User(String hostname, String username, String nickname) {
        this.hostname = hostname;
        this.username = username;
        this.nickname = nickname;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(hostname, user.hostname) && Objects.equals(username, user.username)
                && Objects.equals(nickname, user.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostname, username, nickname);
    }

    @Override
    public String toString() {
        return "{" + " hostname='" + getHostname() + "'" + ", username='" + getUsername() + "'"
                + ", nickname='" + getNickname() + "'" + "}";
    }

}
