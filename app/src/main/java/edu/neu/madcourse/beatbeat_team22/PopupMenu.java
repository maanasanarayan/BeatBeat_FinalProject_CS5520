package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;
import android.widget.Button;

public class PopupMenu extends AppCompatActivity {

    // pop up menu
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    // pop up menu button
    private Button btnResume;
    private Button btnLevelSelector;
    private Button btnLeaderBoard;
    private Button btnGlossary;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openPopUpMenu(View view) {
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
