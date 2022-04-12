package edu.neu.madcourse.beatbeat_team22;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Challenge {
    private List<Integer> notesList = new ArrayList<>();
    private int totalBeats;

    public Challenge() {
    }

    public int getNumNotes() { return this.notesList.size(); }

    public List<Integer> getNotesList() {
        return notesList;
    }

    public void addImage(Integer image) {
        this.notesList.add(image);
        this.totalBeats = notesList.size();
        Log.d("challenge beats", String.valueOf(this.totalBeats));
    }

    public int getTotalBeats() {
        return this.totalBeats; }
}
