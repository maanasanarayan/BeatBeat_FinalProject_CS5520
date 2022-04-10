package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //only here for testing purposes
        openHomepageActivity();
    }

    private void openHomepageActivity() {
        Intent intent = new Intent(this, HomepageActivity.class);
        startActivity(intent);
    }
}