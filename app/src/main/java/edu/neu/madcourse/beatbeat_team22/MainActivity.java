package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.neu.madcourse.beatbeat_team22.model.User;

/*
Shows title activity. If user has logged in before on the same device, proceeds to the
Home Page activity. Otherwise, proceeds to the Login activity.
 */
public class MainActivity extends AppCompatActivity {

    private AlphaAnimation animation1;
    private AlphaAnimation animation2;
    private AlphaAnimation animation3;
    private TextView slogan;
    private TextView name;
    private ImageView logo;
    private static int TIME_OUT = 5000;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USER_KEY = "username";
    private SharedPreferences sharedPreferences;
    private String username;
    private DatabaseReference db;
    private Map<String, User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showTitleAnimation();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        //Getting the session if exists
        sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(USER_KEY, null);

        db = FirebaseDatabase.getInstance().getReference();
        users = new HashMap<>();

        // Retrieve all usernames from the DB
        db.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                User user = dataSnapshot.getValue(User.class);
                users.put(dataSnapshot.getKey(), user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void showTitleAnimation() {
        logo = findViewById(R.id.app_logo);
        name = findViewById(R.id.app_name);
        slogan = findViewById(R.id.app_slogan);

        animation1 = new AlphaAnimation(0.0f, 1.0f);
        animation1.setDuration(1000);
        animation1.setStartOffset(1000);

        animation2 = new AlphaAnimation(0.0f, 1.0f);
        animation2.setDuration(1000);
        animation2.setStartOffset(2000);

        animation3 = new AlphaAnimation(0.0f, 1.0f);
        animation3.setDuration(1000);
        animation3.setStartOffset(3000);

        logo.startAnimation(animation1);
        name.startAnimation(animation2);
        slogan.startAnimation(animation3);

        // Call this after app title is shown
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(username != null) {
                    openHomePageActivity();
                } else {
                    openLoginActivity();
                }
                finish();
            }
        }, TIME_OUT);
    }

    private void openHomePageActivity() {
        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("user", users.get(username));
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}