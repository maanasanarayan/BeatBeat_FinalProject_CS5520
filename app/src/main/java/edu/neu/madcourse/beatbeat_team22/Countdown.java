package edu.neu.madcourse.beatbeat_team22;

import java.util.ArrayList;
import java.util.List;

public class Countdown {
    private List<Integer> ImagesList = new ArrayList<>();

    public Countdown() {
    }

    public void loadImages() {
        ImagesList.add(R.drawable.icons8_three);
        ImagesList.add(R.drawable.icons8_two);
        ImagesList.add(R.drawable.icons8_one);
        ImagesList.add(R.drawable.icons8_go);
    }

    public List<Integer> getImagesList() {
        return ImagesList;
    }
}
