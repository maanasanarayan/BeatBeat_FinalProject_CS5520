package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.beatbeat_team22.model.User;

public class SignupActivity extends AppCompatActivity {

    private EditText fullName;
    private EditText userName;
    private EditText password;
    private EditText confirmPassword;
    private TextView error;
    private DatabaseReference db;
    private List<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullName = findViewById(R.id.fullNameField);
        userName = findViewById(R.id.userNameLogIn);
        password = findViewById(R.id.passwordLogIn);
        confirmPassword = findViewById(R.id.confirmPwdSignUp);
        error = findViewById(R.id.errorLogIn);
        db = FirebaseDatabase.getInstance().getReference();
        usernames = new ArrayList<>();

        // Retrieve all usernames from the DB
        db.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                usernames.add(dataSnapshot.getKey());
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

    /**
     * Method invoked on click of Sign Up button.
     *
     * @param view
     */
    public void onSignUp(View view) {
        String fn = fullName.getText().toString();
        String un = userName.getText().toString();
        String pwd = password.getText().toString();
        String cnfPwd = confirmPassword.getText().toString();

        // Check if inputs are valid
        boolean valid = validateInputs(fn, un, pwd, cnfPwd);
        if(valid) {
            createUser(fn, un, pwd);
        }
    }

    /**
     * Method to create a user on valid inputs.
     *
     * @param name
     * @param username
     * @param pwd
     */
    private void createUser(String name, String username, String pwd) {
        //Create user in Database
        User user = new User(name, username, pwd);
        db.child("Users").child(user.getUsername()).setValue(user);

        //Show a toast to indicate successful registration
        CharSequence text = "Successfully Registered. Login to continue.";
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();

        // Take user to Login activity
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        intent.putExtra("user_details", user);
        startActivity(intent);
    }

    /**
     * Method to validate signup inputs.
     * @param name
     * @param username
     * @param pwd
     * @param confirmPwd
     * @return
     */
    private boolean validateInputs(String name, String username, String pwd, String confirmPwd) {
        if("".equals(name) || "".equals(username)
                || "".equals(pwd) || "".equals(confirmPwd)) {
            error.setText("The fields cannot be empty.");
            return false;
        } else if(! name.matches("[a-zA-Z ]+")) {
            error.setText("Full name can only have letters.");
            return false;
        } else if(usernames.contains(username)) {
            error.setText("Username already exists. Try again.");
            return false;
        } else if(! pwd.equals(confirmPwd)) {
            error.setText("Passwords don't match. Try again.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method invoked on Login link click
     * @param view
     */
    public void loginLinkClick(View view) {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
