package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/*
Shows title activity. If user has logged in before on the same device, proceeds to the
Home Page activity. Otherwise, proceeds to the Login activity.
 */
public class MainActivity extends AppCompatActivity {

    // pop up menu
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    // pop up menu button
    private Button btnResume;
    private Button btnLevelSelector;
    private Button btnLeaderBoard;
    private Button btnGlossary;
    private Button btnExit;

    private AlphaAnimation animation1;
    private AlphaAnimation animation2;
    private AlphaAnimation animation3;
    private TextView slogan;
    private TextView name;
    private ImageView logo;
    private static int TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * logo = findViewById(R.id.app_logo);
         * name = findViewById(R.id.app_name);
         * slogan = findViewById(R.id.app_slogan);
         * 
         * animation1 = new AlphaAnimation(0.0f, 1.0f);
         * animation1.setDuration(1000);
         * animation1.setStartOffset(1000);
         * 
         * animation2 = new AlphaAnimation(0.0f, 1.0f);
         * animation2.setDuration(1000);
         * animation2.setStartOffset(2000);
         * 
         * animation3 = new AlphaAnimation(0.0f, 1.0f);
         * animation3.setDuration(1000);
         * animation3.setStartOffset(3000);
         * 
         * logo.startAnimation(animation1);
         * name.startAnimation(animation2);
         * slogan.startAnimation(animation3);
         * 
         * // Call this after app title is shown
         * new Handler().postDelayed(new Runnable() {
         * 
         * @Override
         * public void run() {
         * openLoginActivity();
         * finish();
         * }
         * }, TIME_OUT);
         */
        Intent intent = new Intent(this, TitleScreenActivity.class);
        startActivity(intent);
        // openLoginActivity();

    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    // pop up menu onClick method
    public void popUpMenu(View view) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpMenuView = getLayoutInflater().inflate(R.layout.popup, null);

        btnResume = popUpMenuView.findViewById(R.id.popupResume);
        btnLevelSelector = popUpMenuView.findViewById(R.id.popupLevelSelector);
        btnLeaderBoard = popUpMenuView.findViewById(R.id.popupLeaderBoard);
        btnGlossary = popUpMenuView.findViewById(R.id.popupGlossary);
        btnExit = popUpMenuView.findViewById(R.id.popupExit);

        dialogBuilder.setView(popUpMenuView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnLevelSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelSelector(view);
            }
        });

        btnLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnGlossary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    // level selector button - open level selector activity
    public void levelSelector(View view) {
        Intent intent = new Intent(this, LevelSelector.class);
        startActivity(intent);
    }
}