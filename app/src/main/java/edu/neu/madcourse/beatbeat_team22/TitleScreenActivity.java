package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreenActivity extends AppCompatActivity {

    Button TitleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        TitleButton = findViewById(R.id.TitleTapButton);
        TitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                //LeaderboardIntent.putExtra("username", user.getUsername());
                startActivity(LoginIntent);
            }
        });
    }
}