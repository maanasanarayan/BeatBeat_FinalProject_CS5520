package edu.neu.madcourse.beatbeat_team22.model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String username;
    private String password;

    public User() {

    }

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User: Full name: ").append(getName());
        sb.append(", Username: ").append(getUsername());
        sb.append(", Password: ").append(getPassword());
        return sb.toString();
    }
}
