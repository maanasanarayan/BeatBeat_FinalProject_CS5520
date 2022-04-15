package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainChallengeActivity extends AppCompatActivity {
    private List<ImageView> nonHighlightedNotes = new ArrayList<>();
    private List<ImageView> highlightedNotes = new ArrayList<>();
    private List<ImageView> countdownImageViews = new ArrayList<>();
    private ImageView firstNote;
    private ImageView secondNote;
    private ImageView thirdNote;
    private ImageView fourthNote;
    private ImageView firstNoteHighlighted;
    private ImageView secondNoteHighlighted;
    private ImageView thirdNoteHighlighted;
    private ImageView fourthNoteHighlighted;
    private ImageView threeView;
    private ImageView twoView;
    private ImageView oneView;
    private ImageView goView;
    private boolean firstClick = true;
    private Button startTapButton;
    private Challenge challenge;
    private Countdown countdown;
    ImageView metronomeRight;
    ImageView metronomeLeft;
    boolean showRight = false;
    Handler handler = new Handler();
    int repeatCount = 0;
    int totalRepeats;
    private int count = 0; // temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge);
        startTapButton = findViewById(R.id.TapButton);
        setStartButton();
        generateChallenge();
        findViews();
        buildImageArrays();
        loadImages();
    }

    private void generateChallenge() {
        challenge = new Challenge(4);
        countdown = new Countdown();
        for (int i=0; i< challenge.getmMeter(); i++) {
            // image credit to icons8.com for free use of their images
            challenge.addNonHighlightedNote(R.drawable.quarter_note);
            challenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
        }
    }

    private void findViews() {
        firstNote = findViewById(R.id.firstNote);
        secondNote = findViewById(R.id.secondNote);
        thirdNote = findViewById(R.id.thirdNote);
        fourthNote = findViewById(R.id.fourthNote);
        firstNoteHighlighted = findViewById(R.id.firstNoteHighlighted);
        secondNoteHighlighted = findViewById(R.id.secondNoteHighlighted);
        thirdNoteHighlighted = findViewById(R.id.thirdNoteHighlighted);
        fourthNoteHighlighted = findViewById(R.id.fourthNoteHighlighted);
        threeView = findViewById(R.id.threeView);
        twoView = findViewById(R.id.twoView);
        oneView = findViewById(R.id.oneView);
        goView = findViewById(R.id.goView);
    }

    private void buildImageArrays() {
        // non-highlighted notes
        nonHighlightedNotes.add(firstNote);
        nonHighlightedNotes.add(secondNote);
        nonHighlightedNotes.add(thirdNote);
        nonHighlightedNotes.add(fourthNote);
        // highlighted notes
        highlightedNotes.add(firstNoteHighlighted);
        highlightedNotes.add(secondNoteHighlighted);
        highlightedNotes.add(thirdNoteHighlighted);
        highlightedNotes.add(fourthNoteHighlighted);
    }

    private void loadImages() {
        countdown.loadImages();
        Log.d("countdownSize", String.valueOf(countdown.getImagesList().size()));
        for (int i=0; i< challenge.getmMeter(); i++) {
            nonHighlightedNotes.get(i).setImageResource(challenge.getNonHighlightedNotes().get(i));
            highlightedNotes.get(i).setImageResource(challenge.getHighlightedNotesList().get(i));
//            countdownImageViews.get(i).setImageResource(countdown.getImagesList().get(i));
        }
    }

    private void runChallenge() throws InterruptedException {
        metronomeRight = findViewById(R.id.metronome);
        metronomeLeft = findViewById(R.id.metronomeLeft);
        handler.postDelayed(toggleM, 0);
    }

    private Runnable toggleM = new Runnable() {
        @Override
        public void run() {
            playNextNote();
//            playCountdown();
            if (repeatCount < challenge.getTotalBeats()) {
                toggleMetronome(showRight);
                handler.postDelayed(this, 1000);
                repeatCount++;
            }
        }
    };

    private void playCountdown() {
        int currImage = repeatCount;
        int prevImage = repeatCount - 1;
        if (repeatCount == 0) {
            countdownImageViews.get(currImage).setVisibility(View.VISIBLE);
        } else if (repeatCount < challenge.getmMeter()) {
            countdownImageViews.get(prevImage).setVisibility(View.INVISIBLE);
            countdownImageViews.get(currImage).setVisibility(View.VISIBLE);
        } else if (repeatCount == challenge.getmMeter()) {
            countdownImageViews.get(currImage).setVisibility(View.INVISIBLE);
        }
    }

    private void toggleMetronome(boolean showLeft) {
        if (showLeft) {
            metronomeRight.setVisibility(View.INVISIBLE);
            metronomeLeft.setVisibility(View.VISIBLE);
        } else {
            metronomeRight.setVisibility(View.VISIBLE);
            metronomeLeft.setVisibility(View.INVISIBLE);
        }
        showRight = !showRight;
    }

    private void playNextNote() {
        if (repeatCount >= challenge.getmMeter()) {
            int currNote = repeatCount % challenge.getmMeter();
            int prevNote = (repeatCount - 1) % challenge.getmMeter();

            if (repeatCount == challenge.getTotalBeats()) { // hide highlight last time
                hideHighlighted(prevNote);
            } else {
                hideHighlighted(prevNote);
                showHighlighted(currNote);
            }
        }
    }

    private void hideHighlighted(int notePos) {
        highlightedNotes.get(notePos).setVisibility(View.INVISIBLE);
        nonHighlightedNotes.get(notePos).setVisibility(View.VISIBLE);
    }

    private void showHighlighted(int notePos) {
        nonHighlightedNotes.get(notePos).setVisibility(View.INVISIBLE);
        highlightedNotes.get(notePos).setVisibility(View.VISIBLE);
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
            calculateScore();
        }
        if (count == 4) {
            Toast.makeText(getApplicationContext(), "Level Complete!", Toast.LENGTH_SHORT).show();
            // launch lesson activity
        }
    }

    private void calculateScore() {
        count ++; // temp
    }

    public void onMenu(View view) {
        // launch menu popup
    }

    public void onRedo(View view) {
        setStartButton();
        count = 0; // temp
    }
}