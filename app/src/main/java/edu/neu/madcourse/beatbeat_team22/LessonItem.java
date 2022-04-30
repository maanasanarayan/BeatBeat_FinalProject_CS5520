package edu.neu.madcourse.beatbeat_team22;

public class LessonItem {
    private String mTitle;
    private int mImage;
    private String mBody;

    public LessonItem(String title, Integer image, String body) {
        mTitle = title;
        mImage = image;
        mBody = body;
    }

    public String getmTitle() {
        return mTitle;
    }

    public int getmImage() {
        return mImage;
    }

    public String getmBody() {
        return mBody;
    }

    public void changeBodyText(String text) { mBody = text; }
}
