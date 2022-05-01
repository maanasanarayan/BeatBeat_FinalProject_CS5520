package edu.neu.madcourse.beatbeat_team22;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.neu.madcourse.beatbeat_team22.model.User;

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
    private TextView errorDescription;
    private ImageView threeView;
    private ImageView twoView;
    private ImageView oneView;
    private ImageView goView;
    private ImageView listenView;
    private ImageView tapView;
    private ImageView redoButton;
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
    private long prevtime = 0;
    private double timingEarlyGate = 0.75;
    private double timingLateGate = 1.25;
    private long milisecondsperbeat = 1000;
    private int currnoteTiming;
    private int requiredScore;
    private int score;

    //popUp menu
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    // pop up menu button
    private Button btnResume;
    private Button btnLevelSelector;
    private Button btnLeaderBoard;
    private Button btnGlossary;
    private Button btnExit;
    private ImageView emoji;

    //user
    private User user;
    private String username;
    DatabaseReference dbRef;

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
        setMediaPlayer();
        String lessonTitle = challenge.getmLessonTitle();
        if (lessonTitle != null) {
            Intent lessonIntent = new Intent(this, LessonActivity.class);
            lessonIntent.putExtra("title", lessonTitle);
            startActivity(lessonIntent);
        }

        Intent intent = getIntent();
        if(intent.hasExtra("user")) {
            user = (User) intent.getSerializableExtra("user");
            Log.d("User details in Main challengea activity","user: " + user);
            username = user.getUsername();
            Log.d(TAG, "username oncreate: " + username);
        }




    }

    private void generateChallenge() {
        requiredScore = 0;
        score = 0;
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
        errorDescription = findViewById(R.id.ErrorDescription);
        threeView = findViewById(R.id.threeView);
        twoView = findViewById(R.id.twoView);
        oneView = findViewById(R.id.oneView);
        goView = findViewById(R.id.goView);
        listenView = findViewById(R.id.listenView);
        tapView = findViewById(R.id.tapView);
        emoji = findViewById(R.id.emoji_face);
        redoButton = findViewById(R.id.redoButton);
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
    }

    private void runChallenge() throws InterruptedException {
        metronomeRight = findViewById(R.id.metronome);
        metronomeLeft = findViewById(R.id.metronomeLeft);
        challengeThread.run();
    }

    private Runnable challengeThread = new Runnable() {
        @Override
        public void run() {
            if (repeatCount >= challenge.getmMeter()) { // after 4 iterations
                playNextNote.run();
            }
            try {
                playCountdown(); // first 4 beats
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (repeatCount < challenge.getTotalBeats()) { // first 4 beats
                toggleMetronome.run();
                handler.postDelayed(this, 1000);
                repeatCount++;
            }
        }
    };

    private void playCountdown() throws IOException {
        int currImage = repeatCount;
        int prevImage = repeatCount - 1;
        if (repeatCount == 0) {
            playWoodblock();
            countdownImageViews.get(currImage).setVisibility(View.VISIBLE);
        } else if (repeatCount < challenge.getmMeter()) {
            countdownImageViews.get(prevImage).setVisibility(View.INVISIBLE);
            countdownImageViews.get(currImage).setVisibility(View.VISIBLE);
            playWoodblock();
        } else if (repeatCount == challenge.getmMeter()) {
            countdownImageViews.get(prevImage).setVisibility(View.INVISIBLE);
        }
    }

    private void playWoodblock() throws IOException {
        playSound(R.raw.woodblock);
    }

    private void playNoteSound() throws IOException {
        playSound(R.raw.piano_note);
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
            if (repeatCount == challenge.getmMeter() * 2) { // when player should tap
                listenView.setVisibility(View.INVISIBLE);
                tapView.setVisibility(View.VISIBLE);
                enableTapButton();
            }

            if (repeatCount == challenge.getTotalBeats()) { // last time
                hideHighlighted(prevNote);
                tapView.setVisibility(View.INVISIBLE);
                hideEmoji();
                enableRedo();
                enableTapButton();
                if (score == requiredScore) {
                    Log.d("score results passed", String.valueOf(score) + " / " + String.valueOf(requiredScore));
                    Toast.makeText(getApplicationContext(), "Level Complete!", Toast.LENGTH_SHORT).show();
                    errorDescription.setText("Level Complete!");
                    errorDescription.setVisibility(View.VISIBLE);
                    // launch lesson activity


                    dbRef = FirebaseDatabase.getInstance().getReference();


                    //username = user.getUsername();

                    Log.d(TAG, "username run: " + username);

                    HashMap User = new HashMap();
                    User.put("levelPassed", currLevel + 1);


                    dbRef.child("Users").child(username).updateChildren(User).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                        }
                    });
                } else {
                    Log.d("score results failed", String.valueOf(score) + " / " + String.valueOf(requiredScore));
                    Toast.makeText(getApplicationContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                }
            } else {
                isPlayed = challenge.getIsNotePlayedList().get(currNote);
                Log.d("isPlayed", String.valueOf(isPlayed));
                if (isPlayed) {
                    try {
                        playNoteSound();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (repeatCount >= challenge.getmMeter() * 2) {
                        requiredScore++;
                        Log.d("score required", String.valueOf(requiredScore));
                    }
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
        prevtime = 0;
        currnoteTiming = 0;
    }

    private void setStartButton() {
        repeatCount = 0;
        firstClick = true;
        startTapButton.setText(R.string.start_string);
        errorDescription.setVisibility(View.INVISIBLE);
        disableRedo();
        generateChallenge();
    }

    private void setMediaPlayer() {
        mp = new MediaPlayer();
        // depreciated API ?
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.reset();
            }
        });
        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mp.start();
            }
        });
    }

    private void playSound(int resID) throws IOException {
        AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(resID);
        if (afd == null) return;
        mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mp.prepareAsync();
    }

    public void onTap(View view){
        if (firstClick) {
            setTapButton();
            disableTapButton();
            try {
                runChallenge();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (repeatCount >= challenge.getmMeter() * 2){
            calculateScore();
        }
        // TODO: add feedback when player fails to tap
    }

    private void calculateScore() {
        long deltatime = System.currentTimeMillis() - prevtime;
        if (errorDescription.getVisibility() == View.VISIBLE) {
            return;
        }
        if (!challenge.getIsNotePlayedList().get(currnoteTiming) && deltatime > milisecondsperbeat * timingLateGate) {
            currnoteTiming++;
            deltatime -= 1000;
        }
        if (!challenge.getIsNotePlayedList().get(currnoteTiming)) {
            showSadFace();
            Log.d("score Incorrect: Don't Tap During a Rest", String.valueOf(score));
            Toast.makeText(getApplicationContext(), "Incorrect! Don't Tap a Rest!", Toast.LENGTH_SHORT).show();
            score--;
            errorDescription.setText("Don't Tap a Rest!");
            errorDescription.setVisibility(View.VISIBLE);
        }
        else if (prevtime == 0) {
            score++;
            showHappyFace();
            Log.d("score Correct", String.valueOf(score));
        }
        else if (deltatime > milisecondsperbeat * timingEarlyGate && deltatime < milisecondsperbeat * timingLateGate) {
            score++;
            showHappyFace();
            Log.d("score Correct", String.valueOf(score));
        } else if (deltatime < milisecondsperbeat *  timingEarlyGate) {
            score--;
            showSadFace();
            Log.d("score Incorrect: Too Early", String.valueOf(score));
            Toast.makeText(getApplicationContext(), "Incorrect! Too Early!", Toast.LENGTH_SHORT).show();
            errorDescription.setText("Too Early!");
            errorDescription.setVisibility(View.VISIBLE);
        } else if (deltatime > milisecondsperbeat *  timingLateGate) {
            score--;
            showSadFace();
            Log.d("score Incorrect: Too Late", String.valueOf(score));
            Toast.makeText(getApplicationContext(), "Incorrect! Too Late!", Toast.LENGTH_SHORT).show();
            errorDescription.setText("Too Late!");
            errorDescription.setVisibility(View.VISIBLE);
        }
        prevtime = System.currentTimeMillis();
        currnoteTiming++;
    }

    public void onMenu(View view) {
        // launch menu popup
        openPopUpMenu(view);
    }

    public void onRedo(View view) {
        setStartButton();
        score = 0; // temp
        Toast.makeText(getApplicationContext(),
                "Level " + String.valueOf(currLevel) + " reset", Toast.LENGTH_SHORT).show();
    }

    private void disableTapButton() {
        startTapButton.setText("");
        startTapButton.setEnabled(false);
    }

    private void enableTapButton() {
        startTapButton.setText(R.string.tap_string);
        startTapButton.setEnabled(true);
    }

    private void disableRedo() {
        redoButton.setEnabled(false);
    }

    private void enableRedo() {
        redoButton.setEnabled(true);
    }

    //pop up menu builder
    public void openPopUpMenu(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpMenuView = getLayoutInflater().inflate(R.layout.popup, null);

        btnResume = popUpMenuView.findViewById(R.id.popupResume);
        btnLevelSelector = popUpMenuView.findViewById(R.id.popupLevelSelector);
        btnLeaderBoard = popUpMenuView.findViewById(R.id.popupLeaderBoard);
        btnGlossary = popUpMenuView.findViewById(R.id.popupGlossary);
        btnExit = popUpMenuView.findViewById(R.id.popupExit);

        dialogBuilder.setView(popUpMenuView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnLevelSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelSelector(view);
            }
        });

        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLeaderBoardFromPopupMenu(view);
            }
        });

        btnGlossary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGlossaryPopupFromMenu(view);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // level selector button - open level selector activity
    public void levelSelector(View view) {
        Intent intent = new Intent(getApplicationContext(), LevelSelector.class);
        Log.d("User details level selector","user: " + user);
        Log.d("User details level selector","user: " + user.getUsername());
        //String userName = user.getUsername();
        intent.putExtra("user", user);
        startActivity(intent);
    }

    // open leader board from pop up menu
    public void openLeaderBoardFromPopupMenu(View view) {
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    // open glossary from pop upmenu
    public void openGlossaryPopupFromMenu(View view) {
        Intent intent = new Intent(this, GlossaryActivity.class);
        startActivity(intent);
    }

    private void showHappyFace() {
        emoji.setImageResource(R.drawable.happy_face_foreground);
        emoji.setVisibility(View.VISIBLE);
    }

    private void hideEmoji() {
        emoji.setVisibility(View.INVISIBLE);
    }

    private void showSadFace() {
        emoji.setImageResource(R.drawable.sad_face_foreground);
        emoji.setVisibility(View.VISIBLE);
    }

}