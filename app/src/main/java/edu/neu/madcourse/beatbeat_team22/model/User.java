package edu.neu.madcourse.beatbeat_team22.model;

import java.io.Serializable;

public class User implements Serializable {

    private String fullname;
    private String username;
    private String password;

    public User(String name, String username, String password) {
        this.fullname = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }
}
