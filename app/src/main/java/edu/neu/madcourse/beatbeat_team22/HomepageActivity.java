package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import edu.neu.madcourse.beatbeat_team22.model.User;

public class HomepageActivity extends AppCompatActivity {

    Button StartButton;
    Button LeaderboardButton;
    Button GlossaryButton;
    Button LevelSelectButton;
    Button LogoutButton;
    Button DailyChallengeButton;
    SharedPreferences sharedPreferences;
    Integer currLevel;
    int counter = 0;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent = getIntent();
        if(intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            Log.d("User details","user: " + user);
        }

        StartButton = findViewById(R.id.StartButton);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StartIntent = new Intent(getApplicationContext(), MainChallengeActivity.class);
                currLevel = 1; // TODO: pull from DB
                StartIntent.putExtra("level", currLevel);
                startActivity(StartIntent);
            }
        });

        LeaderboardButton = findViewById(R.id.LeaderboardButton);
        LeaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LeaderboardIntent = new Intent(getApplicationContext(), LeaderboardActivity.class);
                //LeaderboardIntent.putExtra("username", user.getUsername());
                startActivity(LeaderboardIntent);
            }
        });

        LevelSelectButton = findViewById(R.id.LevelSelectButton);
        LevelSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LevelSelectIntent = new Intent(getApplicationContext(), LevelSelector.class);
                //LeaderboardIntent.putExtra("username", user.getUsername());
                startActivity(LevelSelectIntent);
            }
        });

        GlossaryButton = findViewById(R.id.GlossaryButton);
        GlossaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GlossaryIntent = new Intent(getApplicationContext(), GlossaryActivity.class);
                //LeaderboardIntent.putExtra("username", user.getUsername());
                startActivity(GlossaryIntent);
            }
        });


        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE);

        LogoutButton = findViewById(R.id.LogoutButton);
        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                Intent LogoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(LogoutIntent);
            }
        });

        Integer minutes = Integer.valueOf(LocalDateTime.now().getMinute());
        int level = getLevel(minutes);
        DailyChallengeButton = findViewById(R.id.DailyChallenge);
        DailyChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DailyChallengeIntent = new Intent(getApplicationContext(), MainChallengeActivity.class);
                DailyChallengeIntent.putExtra("level", level);
                DailyChallengeIntent.putExtra("dailyChallenge", "true");
                startActivity(DailyChallengeIntent);
            }
        });
    }

    private int getLevel(Integer mins) {
        if((mins <= 5) || (mins > 20 && mins <= 25) || (mins > 40 && mins <= 45)) {
            return 1;
        } else if((mins > 5 && mins <= 10) || (mins > 25 && mins <= 30) || (mins > 45 && mins <= 50)) {
            return 2;
        } else if((mins > 10 && mins <= 15) || (mins > 30 && mins <= 35) || (mins > 50 && mins <= 55)) {
            return 3;
        } else {
            return 4;
        }
    }

    @Override
    public void onBackPressed() {
        counter++;
        if(counter == 2) {
            moveTaskToBack(true);
        }
    }

}