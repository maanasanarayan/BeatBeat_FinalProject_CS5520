package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LessonActivity extends AppCompatActivity {
    private String mLessonName;
    private TextView mTitleTextView;
    private ImageView mImageView;
    private TextView mBodyTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        init();
    }

    private void init() {
        mLessonName = (String) getIntent().getSerializableExtra("title");
        findViews();
        loadLesson();
    }

    public void findViews() {
        mTitleTextView = findViewById(R.id.LessonTitle);
        mImageView = findViewById(R.id.LessonImage);
        mBodyTextView = findViewById(R.id.LessonBody);
        button = findViewById(R.id.continueButton);
    }

    public void loadLesson() {
        switch (mLessonName) {
            case "The Quarter Note":
                setResources(mLessonName, R.drawable.quarter_note, getString(R.string.quarter_note_body));
            break;
            case "The Quarter Rest":
                setResources(mLessonName, R.drawable.quarter_rest_90, getString(R.string.quarter_rest_body));
            break;
        }
    }

    private void setResources(String title, int image, String body) {
        mTitleTextView.setText(title);
        mImageView.setImageResource(image);
        mBodyTextView.setText(body);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}