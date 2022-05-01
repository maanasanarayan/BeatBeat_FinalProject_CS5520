package edu.neu.madcourse.beatbeat_team22.model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String username;
    private String password;
    private Integer levelPassed;

    public User() {

    }

    public User(String name, String username, String password, Integer levelPassed) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.levelPassed = levelPassed;
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

    public Integer getLevelPassed() {
        return levelPassed;
    }

    public void setLevelPassed(Integer level) {
        this.levelPassed = level;
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
