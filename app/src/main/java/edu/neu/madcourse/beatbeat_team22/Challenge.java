package edu.neu.madcourse.beatbeat_team22;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    private List<Integer> notesList = new ArrayList<>();
    private List<Boolean> isNotePlayedList = new ArrayList<>();
    private List<Integer> highlightedNotesList = new ArrayList<>();
    private int totalBeats;
    private int mMeter;
    private boolean passed;
    private String mLessonTitle;

    public Challenge(int meter) {
        this.mMeter = meter;
    }

    public int getNumNotes() {
        return this.notesList.size();
    }

    public List<Integer> getNonHighlightedNotes() {
        return this.notesList;
    }

    public void addNonHighlightedNote(Integer image, Boolean notePlayed) {
        this.isNotePlayedList.add(notePlayed);
        this.notesList.add(image);
    }

    public List<Integer> getHighlightedNotesList() {
        return this.highlightedNotesList;
    }

    public List<Boolean> getIsNotePlayedList() {
        return this.isNotePlayedList;
    }

    public String getmLessonTitle() {
        return mLessonTitle;
    }

    public void setmLessonTitle(String mLessonTitle) {
        this.mLessonTitle = mLessonTitle;
    }

    public void addHighlightedNote(Integer image) {
        this.highlightedNotesList.add(image);
    }

    public int getTotalBeats() {
        this.totalBeats = mMeter * 3;
        return this.totalBeats;
    }

    public int getmMeter() {
        return this.mMeter;
    }
}
