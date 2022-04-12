package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainChallengeActivity extends AppCompatActivity {
    private boolean firstClick = true;
    private Button startTapButton;
    private Challenge challenge;
    ImageView metronome;
    private int count = 0; // temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge);
        startTapButton = findViewById(R.id.TapButton);
        setStartButton();
        tempGenerateChallenge();
    }

    private void tempGenerateChallenge() {
        challenge = new Challenge();
        challenge.addImage(R.drawable.quarter_note);
        challenge.addImage(R.drawable.quarter_note);
        challenge.addImage(R.drawable.quarter_note);
        challenge.addImage(R.drawable.quarter_note);
    }

    private void runChallenge() throws InterruptedException {
        metronome = findViewById(R.id.metronome);

//        for (int i=0; i < challenge.getTotalBeats(); i++) {
//            switchMetronome(i);
//            Thread.sleep(1000);
//        }
    }

    private void switchMetronome(int iteration) {
        if (iteration % 2 == 0) {
            metronome.setImageResource(R.drawable.metronome_left);
        } else {
            metronome.setImageResource(R.drawable.metronome_right);
        }
    }

    private void setTapButton() {
        firstClick = false;
        startTapButton.setText(R.string.tap_string);
    }

    private void setStartButton() {
        firstClick = true;
        startTapButton.setText(R.string.start_string);
    }

    public void onTap(View view) throws InterruptedException {
        if (firstClick) {
            setTapButton();
            runChallenge();
        } else {
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
        setStartButton();
        count = 0; // temp
    }
}