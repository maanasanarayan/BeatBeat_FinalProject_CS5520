package edu.neu.madcourse.beatbeat_team22;

import java.io.Serializable;

public class LeaderboardCard implements Serializable {
    private String userName;

    public LeaderboardCard() { }
    public LeaderboardCard(String userName) {
        this.userName = userName;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_card);
    }*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
