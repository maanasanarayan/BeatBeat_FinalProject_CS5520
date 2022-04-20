package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.logging.Level;

public class HomepageActivity extends AppCompatActivity {

    Button LeaderboardButton;
    Button GlossaryButton;
    Button LevelSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

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
    }


}