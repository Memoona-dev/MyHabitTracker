package com.app.myhabittracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.myhabittracker.models.User;
import com.app.myhabittracker.utils.Constants;
import com.app.myhabittracker.utils.PrefUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class Login extends AppCompatActivity {

    TextView regBtn;
    TextInputEditText email;
    TextInputEditText password;
    MaterialButton button;

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    PrefUtils pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        regBtn = findViewById(R.id.reg_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button); // Ensure this matches your XML ID

        pref = PrefUtils.getInstance(this);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        button.setOnClickListener(view -> signinUser());

        regBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });
    }

    private void signinUser() {
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();

        auth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnSuccessListener(authResult -> {
                    firestore.collection(Constants.usersCollection)
                            .document(authResult.getUser().getUid()).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                User user = documentSnapshot.toObject(User.class);

                                if (pref.setUser(user)) {
                                    Intent intent = new Intent(Login.this, AllHabit.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("user", user);
                                    intent.putExtra("activity", "Login Activity");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(e -> Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show());
                })
                .addOnFailureListener(e -> Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}