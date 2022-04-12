package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.logging.Level;

public class PopUps extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_ups);
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.congratsMsg:
                Toast.makeText(this, "congrats", Toast.LENGTH_LONG).show();
                return true;
            case R.id.nextLevel:
                Toast.makeText(this, "Next Level", Toast.LENGTH_LONG).show();
                return true;
            case R.id.replay:
                Toast.makeText(this, "Replay", Toast.LENGTH_LONG).show();
                return true;
            case R.id.allLevels:
                Intent intent = new Intent(this, LevelSelector.class);
                startActivity(intent);
            default:
                return false;

        }
    }
}