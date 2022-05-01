package edu.neu.madcourse.beatbeat_team22;

import android.util.Log;
import android.widget.ImageView;

public class ChallengeGenerator {
    private int currLevel;
    private Challenge thisChallenge;

    public ChallengeGenerator(int level) {
        this.currLevel = level;
    }

    public Challenge buildChallenge() {
        createLevel();
        Log.d("levelBeforeSending", String.valueOf(thisChallenge.getNonHighlightedNotes()));
        return thisChallenge;
    }

    private void createLevel() {
        switch (currLevel) {
            case 1: buildLevel1();
            break;
            case 2: buildLevel2();
            break;
            case 3: buildLevel3();
            break;
            case 4: buildLevel4();
            break;
        }
    }

    private void buildLevel1() {
        thisChallenge = new Challenge(4);
        for (int i=0; i< thisChallenge.getmMeter(); i++) {
            // image credit to icons8.com for free use of their images
            thisChallenge.addNonHighlightedNote(R.drawable.quarter_note, true);
            thisChallenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
            thisChallenge.setmLessonTitle("The Quarter Note");
        }
    }

    private void buildLevel2() {
        thisChallenge = new Challenge(4);
        thisChallenge.setmLessonTitle("The Quarter Rest");
        for (int i=0; i< thisChallenge.getmMeter(); i++) {
            switch (i) {
                case 0: case 1: case 2:
                    thisChallenge.addNonHighlightedNote(R.drawable.quarter_note, true);
                    thisChallenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
                break;
                case 3:
                    thisChallenge.addNonHighlightedNote(R.drawable.quarter_rest_90, false);
                    thisChallenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
            }
        }
    }

    private void buildLevel3() {
        Log.d("currLevel", String.valueOf(currLevel));
        thisChallenge = new Challenge(4);
        for (int i=0; i< thisChallenge.getmMeter(); i++) {
            switch (i) {
                case 0: case 1: case 3:
                    thisChallenge.addNonHighlightedNote(R.drawable.quarter_note, true);
                    thisChallenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
                    break;
                case 2:
                    thisChallenge.addNonHighlightedNote(R.drawable.quarter_rest_90, false);
                    thisChallenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
                    break;
            }
        }
    }

    private void buildLevel4() {
        Log.d("currLevel", String.valueOf(currLevel));
        thisChallenge = new Challenge(4);
        for (int i=0; i< thisChallenge.getmMeter(); i++) {
            switch (i) {
                case 1:
                    thisChallenge.addNonHighlightedNote(R.drawable.quarter_note, true);
                    thisChallenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
                    break;
                case 0: case 2: case 3:
                    thisChallenge.addNonHighlightedNote(R.drawable.quarter_rest_90, false);
                    thisChallenge.addHighlightedNote(R.drawable.quarter_note_highlighted);
                    break;
            }
        }
    }

    public Integer maxGameLevel() {
        return 4;
    }

}
