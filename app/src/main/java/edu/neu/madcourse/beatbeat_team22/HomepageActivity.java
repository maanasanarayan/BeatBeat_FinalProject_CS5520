package edu.neu.madcourse.beatbeat_team22;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    DatabaseReference dbRef;
    String username;


    public static final String EXTRA_USERNAME = "edu.neu.madcourse.beatbeat_team22.EXTRA_USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent = getIntent();
        if(intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            Log.d("User details","user: " + user);
            readLevelProgression();
        }

        StartButton = findViewById(R.id.StartButton);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StartIntent = new Intent(getApplicationContext(), MainChallengeActivity.class);
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

        Integer minutes = Integer.valueOf(LocalDateTime.now().getMinute());
        int level = getLevel(minutes);
        DailyChallengeButton = findViewById(R.id.DailyChallenge);
        DailyChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent DailyChallengeIntent = new Intent(getApplicationContext(), MainChallengeActivity.class);
                DailyChallengeIntent.putExtra("level", level);
                DailyChallengeIntent.putExtra("dailyChallenge", "true");
                DailyChallengeIntent.putExtra("user", user);
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


    public void readLevelProgression () {


        dbRef = FirebaseDatabase.getInstance().getReference();
        username = user.getUsername();

        Log.d(TAG, "readLevelDB user name: " + username);
        Log.d(TAG, "readLevelDB child user status: " + dbRef.child("Users").child(username));


        dbRef.child("Users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot snapshot = task.getResult();
                        if(snapshot.child("levelPassed").exists()) {
                            String level = String.valueOf(snapshot.child("levelPassed").getValue());
                            currLevel = Integer.parseInt(level);
                        } else {
                            currLevel = 1;
                        }

                        Log.d(TAG, "You are at level " + currLevel);


                    } else {
                        Toast.makeText(HomepageActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}