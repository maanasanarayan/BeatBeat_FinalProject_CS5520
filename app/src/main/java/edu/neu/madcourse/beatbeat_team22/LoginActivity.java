package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.beatbeat_team22.model.User;

public class LoginActivity extends AppCompatActivity {

    private User user;
    private EditText username;
    private EditText password;
    private DatabaseReference db;
    private List<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userNameLogIn);
        password = findViewById(R.id.passwordLogIn);

        // Setting user details if they signed up in same session.
        Intent intent = getIntent();
        if(intent.hasExtra("user_details")) {
            user = (User) intent.getSerializableExtra("user_details");
            username.setText(user.getUsername());
            password.setText(user.getPassword());
        }

        db = FirebaseDatabase.getInstance().getReference();
        usernames = new ArrayList<>();

        // Retrieve all usernames from the DB
        db.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                usernames.add(dataSnapshot.getKey());
                User user = dataSnapshot.getValue(User.class);
                Log.d("Snapshot", user.toString());
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

    public void onSignUpClick(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void onLogIn(View view) {
        String un = username.getText().toString();
        String pwd = password.getText().toString();

        boolean valid = validateInputs(un, pwd);
    }

    private boolean validateInputs(String userName, String pwd) {
        return true;
    }
}