package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.neu.madcourse.beatbeat_team22.model.User;

public class SignupActivity extends AppCompatActivity {

    EditText fullName;
    EditText userName;
    EditText password;
    EditText confirmPassword;
    TextView error;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullName = findViewById(R.id.fullNameField);
        userName = findViewById(R.id.userNameSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmPassword = findViewById(R.id.confirmPwdSignUp);
        error = findViewById(R.id.errorSignUp);
        db = FirebaseDatabase.getInstance().getReference();

        userName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                checkUserNameExists(userName.getText().toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void onSignUp(View view) {
        String fn = fullName.getText().toString();
        String un = userName.getText().toString();
        String pwd = password.getText().toString();
        String cnfPwd = confirmPassword.getText().toString();

        boolean valid = validateInputs(fn, un, pwd, cnfPwd);
        if(valid) {
            createUser(fn, un, pwd);
        }
    }

    private void createUser(String name, String username, String pwd) {
        User user = new User(name, username, pwd);
        db.child("Users").child(user.getUsername()).setValue(user);

        // Returning to Login activity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("user_details", user);
        startActivity(intent);
    }

    private boolean validateInputs(String name, String username, String pwd, String confirmPwd) {
        if("".equals(name) || "".equals(username)
                || "".equals(pwd) || "".equals(confirmPwd)) {
            error.setText("The fields cannot be empty.");
            return false;
        } else if(! name.matches("[a-zA-Z ]+")) {
            error.setText("Full name can only have letters.");
            return false;
        } else if(checkUserNameExists(username)) {
            error.setText("Username already exists. Try again.");
            return false;
        } else if(! pwd.equals(confirmPwd)) {
            error.setText("Passwords don't match. Try again.");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkUserNameExists(String username) {
        final boolean[] exists = {false};
        db.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Snap", "Snap: "+dataSnapshot);
                if(dataSnapshot.exists()){
                    Log.d("Snap", "Snap: exists");
                    exists[0] = true;
                } else {
                    // User does not exist. NOW call createUserWithEmailAndPassword
                    Log.d("Snap", "Snap: does not exist");
                    exists[0] = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return exists[0];
    }
}