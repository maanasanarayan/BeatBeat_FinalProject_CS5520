package edu.neu.madcourse.beatbeat_team22;

import static java.lang.Integer.parseInt;

import android.util.Log;

public class LevelSelectorItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private Integer mLevel;

    public LevelSelectorItem(int imageResource, String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mLevel = parseLevel(text1);
    }

    private int parseLevel(String text) {
        try {
            mLevel = Integer.parseInt(text.substring(text.length() - 1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("LevelSelectorItem text1 does not end with integer");
        }
        return mLevel;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }

    public Integer getmLevel() {
        return mLevel;
    }
}
