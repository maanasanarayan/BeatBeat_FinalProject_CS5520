package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.level1:
                //openlevel  activity
                break;
            case R.id.level2:
                //openlevel  activity
                break;
            case R.id.level3:
                //openlevel  activity
                break;
            case R.id.level4:
                //openlevel  activity
                break;
            case R.id.level5:
                //openlevel  activity
                break;
            case R.id.level6:
                //openlevel  activity
                break;
            case R.id.level7:
                //openlevel  activity
                break;
            case R.id.level8:
                //openlevel  activity
                break;
            case R.id.level9:
                //openlevel  activity
                break;
            case R.id.level10:
                //openlevel  activity
                break;
        }
    }
}