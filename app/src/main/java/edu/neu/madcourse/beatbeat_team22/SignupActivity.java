package edu.neu.madcourse.beatbeat_team22;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    EditText fullName;
    EditText userName;
    EditText password;
    EditText confirmPassword;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void onSignUp(View view) {
        validateInputs();
    }

    private void validateInputs() {
        fullName = findViewById(R.id.fullNameField);
        userName = findViewById(R.id.userNameSignUp);
        password = findViewById(R.id.passwordSignUp);
        confirmPassword = findViewById(R.id.confirmPwdSignUp);
        error = findViewById(R.id.errorSignUp);
    }
}