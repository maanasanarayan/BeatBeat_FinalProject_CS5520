package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainChallengeActivity extends AppCompatActivity {
    private boolean firstClick = true;
    private Button startTapButton;
    private int count = 0; // temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge);
        startTapButton = findViewById(R.id.TapButton);
        setStart();
    }

    private void runChallenge() {
        // logic for this level
    }

    private void setTap() {
        firstClick = false;
        startTapButton.setText(R.string.tap_string);
    }

    private void setStart() {
        firstClick = true;
        startTapButton.setText(R.string.start_string);
    }

    public void onTap(View view) {
        if (firstClick) {
            setTap();
        } else {
            runChallenge();
            count ++; // temp
        }
        if (count == 4) {
            Toast.makeText(getApplicationContext(), "Level Complete!", Toast.LENGTH_SHORT).show();
            // launch lesson activity
        }
    }

    public void onMenu(View view) {
        // launch menu popup
    }

    public void onRedo(View view) {
        setStart();
        count = 0; // temp
    }
}