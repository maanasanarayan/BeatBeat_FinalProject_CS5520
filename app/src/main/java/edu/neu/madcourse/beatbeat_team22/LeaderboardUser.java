package edu.neu.madcourse.beatbeat_team22;

import java.io.Serializable;

public class LeaderboardUser implements Serializable {
    private String username;

    public LeaderboardUser() {

    }

    public LeaderboardUser(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Name: " + getUsername();
    }
}
