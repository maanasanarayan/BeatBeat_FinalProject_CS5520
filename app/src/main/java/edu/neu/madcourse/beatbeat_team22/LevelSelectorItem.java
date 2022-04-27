package edu.neu.madcourse.beatbeat_team22;

import static java.lang.Integer.parseInt;

import android.util.Log;

public class LevelSelectorItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private Integer mLevel;
    private Integer mNextLevel;
    private boolean mLevelUnlocked;

    public LevelSelectorItem(int imageResource, String text1, String text2, boolean levelUnlocked) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        mLevel = parseLevel(text1);
        mNextLevel = mLevel + 1;
        mLevelUnlocked = levelUnlocked;
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

    public Integer getmNextLevel() {
        return mNextLevel;
    }

    public boolean getLevelUnlocked() { return mLevelUnlocked; }

    public void setLevelUnlocked() {
        this.mLevelUnlocked = true;
    }


}
