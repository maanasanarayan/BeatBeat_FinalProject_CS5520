package edu.neu.madcourse.beatbeat_team22;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    private List<Integer> notesList = new ArrayList<>();
    private int totalBeats;
    private int mMeter;

    public Challenge(int meter) {
        this.mMeter = meter;
    }

    public int getNumNotes() { return this.notesList.size(); }

    public List<Integer> getNotesList() {
        return notesList;
    }

    public void addImage(Integer image) {
        this.notesList.add(image);

    }

    public int getTotalBeats() {
        this.totalBeats = mMeter * 3;
        Log.d("challenge beats", String.valueOf(this.totalBeats));
        return this.totalBeats;
    }
}
