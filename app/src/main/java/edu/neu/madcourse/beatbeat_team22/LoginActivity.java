package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.neu.madcourse.beatbeat_team22.model.User;

public class LoginActivity extends AppCompatActivity {

    private User user;
    private EditText username;
    private EditText password;
    private TextView error;
    private DatabaseReference db;
    private Map<String, User> users;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userNameLogIn);
        password = findViewById(R.id.passwordLogIn);
        error = findViewById(R.id.errorLogIn);
        sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE);

        // Setting user details if they signed up in same session.
        Intent intent = getIntent();
        if(intent.hasExtra("user_details")) {
            user = (User) intent.getSerializableExtra("user_details");
            username.setText(user.getUsername());
            password.setText(user.getPassword());
        }

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
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, MainChallengeActivity.class);
        startActivity(intent);
    }

    public void onSignUpClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onLogIn(View view) {
        String un = username.getText().toString();
        String pwd = password.getText().toString();

        boolean valid = validateInputs(un, pwd);
        if(valid) {
            //Set shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(MainActivity.USER_KEY, un);
            editor.apply();

            //Open home page activity
            Intent intent = new Intent(this, HomepageActivity.class);
            intent.putExtra("user", users.get(un));
            startActivity(intent);
        }
    }

    private boolean validateInputs(String userName, String pwd) {
        if("".equals(userName) || "".equals(pwd)) {
            error.setText("Fields cannot be empty.");
            return false;
        } else if(! users.containsKey(userName)) {
            error.setText("User does not exist. Try signing up.");
            return false;
        } else if(! passwordMatch(userName, pwd)) {
            error.setText("Password incorrect. Try again.");
            return false;
        } else {
            return true;
        }
    }

    private boolean passwordMatch(String un, String pwd) {
        User user = users.get(un);
        if(user.getPassword().equals(pwd)) {
            return true;
        } else {
            return false;
        }
    }

}