package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
Shows title activity. If user has logged in before on the same device, proceeds to the
Home Page activity. Otherwise, proceeds to the Login activity.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}