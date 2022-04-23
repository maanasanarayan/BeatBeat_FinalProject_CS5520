package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
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
    private ImageView listenView;
    private ImageView tapView;
    private boolean firstClick;
    private Button startTapButton;
    private Integer currLevel;
    private ChallengeGenerator challengeGenerator;
    private Challenge challenge;
    private Countdown countdown;
    ImageView metronomeRight;
    ImageView metronomeLeft;
    boolean showLeft = false;
    Handler handler = new Handler();
    int repeatCount;
    // credit to findsounds.com for free use of their sounds
    private MediaPlayer mp;
    private Boolean isPlayed;
    private int score = 0; // temp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_challenge);
        startTapButton = findViewById(R.id.TapButton);
        generateChallenge();
        findViews();
        buildImageArrays();
        loadImages();
        setStartButton();
    }

    private void generateChallenge() {
        currLevel = (Integer) getIntent().getSerializableExtra("level");
        challengeGenerator = new ChallengeGenerator(currLevel);
        challenge = challengeGenerator.buildChallenge();
        Log.d("levelAfterGenerate", String.valueOf(challenge.getNonHighlightedNotes()));
        countdown = new Countdown();
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
        listenView = findViewById(R.id.listenView);
        tapView = findViewById(R.id.tapView);
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
        // countdown images
        countdownImageViews.add(threeView);
        countdownImageViews.add(twoView);
        countdownImageViews.add(oneView);
        countdownImageViews.add(goView);
    }

    private void loadImages() {
        countdown.loadImages();
        listenView.setImageResource(R.drawable.listen_icon);
        tapView.setImageResource(R.drawable.tap_icon);
        for (int i=0; i<challenge.getmMeter(); i++) {
            nonHighlightedNotes.get(i).setImageResource(challenge.getNonHighlightedNotes().get(i));
            highlightedNotes.get(i).setImageResource(challenge.getHighlightedNotesList().get(i));
            countdownImageViews.get(i).setImageResource(countdown.getImagesList().get(i));
        }
        Log.d("noteList level", String.valueOf(challenge.getNonHighlightedNotes()));
    }

    private void runChallenge() throws InterruptedException {
        metronomeRight = findViewById(R.id.metronome);
        metronomeLeft = findViewById(R.id.metronomeLeft);
        challengeThread.run();
    }

    private Runnable challengeThread = new Runnable() {
        @Override
        public void run() {
            Log.d("repeatCount", String.valueOf(repeatCount));
            if (repeatCount >= challenge.getmMeter()) {
                playNextNote.run();
            }
            playCountdown();
            if (repeatCount < challenge.getTotalBeats()) {
                toggleMetronome.run();
                handler.postDelayed(this, 1000);
                repeatCount++;
            }
        }
    };

    private void playCountdown() {
        Log.d("repeatCount countdown", String.valueOf(repeatCount));
        int currImage = repeatCount;
        int prevImage = repeatCount - 1;
        if (repeatCount == 0) {
            countdownImageViews.get(currImage).setVisibility(View.VISIBLE);
            playWoodblock();
        } else if (repeatCount < challenge.getmMeter()) {
            countdownImageViews.get(prevImage).setVisibility(View.INVISIBLE);
            countdownImageViews.get(currImage).setVisibility(View.VISIBLE);
            playWoodblock();
        } else if (repeatCount == challenge.getmMeter()) {
            countdownImageViews.get(prevImage).setVisibility(View.INVISIBLE);
        }
    }

    private void playWoodblock() {
        mp = MediaPlayer.create(this, R.raw.woodblock);
        mp.start();
        // depreciated API ?
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.stop();
                mp.release();
            }
        });
    }

    private void playNoteSound() {
        mp = MediaPlayer.create(this, R.raw.arp);
        mp.start();
    }

    private Runnable toggleMetronome = new Runnable() {
        @Override
        public void run() {
            if (showLeft) {
                metronomeRight.setVisibility(View.INVISIBLE);
                metronomeLeft.setVisibility(View.VISIBLE);
            } else {
                metronomeRight.setVisibility(View.VISIBLE);
                metronomeLeft.setVisibility(View.INVISIBLE);
            }
            showLeft = !showLeft;
        }
    };

    private Runnable playNextNote = new Runnable() {
        @Override
        public void run() {
            int currNote = repeatCount % challenge.getmMeter();
            int prevNote = (repeatCount - 1) % challenge.getmMeter();

            if (repeatCount == challenge.getmMeter()) {
                listenView.setVisibility(View.VISIBLE);
            }
            if (repeatCount == challenge.getmMeter() * 2) {
                listenView.setVisibility(View.INVISIBLE);
                tapView.setVisibility(View.VISIBLE);
            }

            if (repeatCount == challenge.getTotalBeats()) { // hide highlight last time
                hideHighlighted(prevNote);
                tapView.setVisibility(View.INVISIBLE);
            } else {
                isPlayed = challenge.getIsNotePlayedList().get(currNote);
                Log.d("isPlayed", String.valueOf(isPlayed));
                if (isPlayed) {
                    playNoteSound();
                }
                hideHighlighted(prevNote);
                showHighlighted(currNote);
            }
        }
    };

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
        repeatCount = 0;
        firstClick = true;
        startTapButton.setText(R.string.start_string);
        generateChallenge();
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
        if (score == challenge.getmMeter()) {
            Toast.makeText(getApplicationContext(), "Level Complete!", Toast.LENGTH_SHORT).show();
            // launch lesson activity
        }
    }

    private void calculateScore() {
        if (isPlayed) {
            score++;
            Log.d("score Correct", String.valueOf(score));
        } else {
            score--;
            Log.d("score Incorrect", String.valueOf(score));
            Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onMenu(View view) {
        // launch menu popup
    }

    public void onRedo(View view) {
        setStartButton();
        score = 0; // temp
        Toast.makeText(getApplicationContext(),
                "Level " + String.valueOf(currLevel) + " reset", Toast.LENGTH_SHORT).show();
    }
}