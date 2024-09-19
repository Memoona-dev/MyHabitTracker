package com.app.myhabittracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private TextInputEditText nameEditText, phoneEditText, emailEditText, passwordEditText;
    private Button signUpButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);// Make sure this is the correct XML layout name

        auth = FirebaseAuth.getInstance();

        // Initialize the views
        nameEditText = findViewById(R.id.name_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        signUpButton = findViewById(R.id.signup_btn);

        // Set an OnClickListener on the SignUp button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSignUp();
            }
        });
    }

    private void validateAndSignUp() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Simple validation for empty fields
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(SignUp.this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(SignUp.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(SignUp.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(SignUp.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform the sign-up process here (e.g., save data to database, or send to server)

        // Show a success message
        Toast.makeText(SignUp.this, "Sign Up successful!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SignUp.this, NewHabit.class);
        startActivity(intent);

        // Optionally, finish the current activity so the user cannot go back to the sign-up screen
        finish();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign-up success, navigate to the NewHabit activity
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(SignUp.this, "Sign Up successful!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUp.this, NewHabit.class);
                            startActivity(intent);

                            // Optionally, finish the current activity so the user cannot go back to the sign-up screen
                            finish();
                        } else {
                            // If sign-up fails, display a message to the user.
                            Toast.makeText(SignUp.this, "Sign Up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
