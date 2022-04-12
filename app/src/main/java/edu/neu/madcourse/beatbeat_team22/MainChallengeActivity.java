package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainChallengeActivity extends AppCompatActivity {
    private boolean firstClick = true;
    private Button startTapButton;
    private Challenge challenge;
    ImageView metronomeRight;
    ImageView metronomeLeft;
    boolean showRight = false;
    Handler handler = new Handler();
    int repeats = 0;
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
        challenge = new Challenge(4);
        challenge.addImage(R.drawable.quarter_note);
        challenge.addImage(R.drawable.quarter_note);
        challenge.addImage(R.drawable.quarter_note);
        challenge.addImage(R.drawable.quarter_note);
    }

    private void runChallenge() throws InterruptedException {
        metronomeRight = findViewById(R.id.metronome);
        metronomeLeft = findViewById(R.id.metronomeLeft);

        int runCount = challenge.getTotalBeats();
        handler.postDelayed(toggleM, 0);
    }

    private Runnable toggleM = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "changing metro", Toast.LENGTH_SHORT).show();
            toggleMetronome(showRight);
            showRight = !showRight;
            if (repeats < challenge.getTotalBeats()) {
                handler.postDelayed(this, 1000);
                repeats++;
            }
        }
    };

    private void toggleMetronome(boolean showLeft) {
        if (showLeft) {
            metronomeRight.setVisibility(View.INVISIBLE);
            metronomeLeft.setVisibility(View.VISIBLE);
        } else {
            metronomeRight.setVisibility(View.VISIBLE);
            metronomeLeft.setVisibility(View.INVISIBLE);
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

    public void onTap(View view){
        if (firstClick) {
            setTapButton();
            try {
                runChallenge();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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