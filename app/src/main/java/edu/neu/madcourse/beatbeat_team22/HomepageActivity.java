package edu.neu.madcourse.beatbeat_team22;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    Random random;


    public static final String EXTRA_USERNAME = "edu.neu.madcourse.beatbeat_team22.EXTRA_USERNAME";
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
                StartIntent.putExtra("user", user);
                Log.d(TAG, "onClick: " + user.getUsername());
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
                Intent intent = new Intent(getApplicationContext(), LevelSelector.class);
                //LeaderboardIntent.putExtra("username", user.getUsername());
                Log.d("User details level selector","user: " + user);
                Log.d("User details level selector","user: " + user.getUsername());
                String userName = user.getUsername();
                intent.putExtra("user", user);
                startActivity(intent);
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

        DailyChallengeButton = findViewById(R.id.DailyChallenge);
        DailyChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent DailyChallenge = new Intent(getApplicationContext(), DailyChallengeActivity.class);
//                startActivity(DailyChallenge);
            }
        });
    }

    @Override
    public void onBackPressed() {
        counter++;
        if(counter == 2) {
            moveTaskToBack(true);
        }
    }

}