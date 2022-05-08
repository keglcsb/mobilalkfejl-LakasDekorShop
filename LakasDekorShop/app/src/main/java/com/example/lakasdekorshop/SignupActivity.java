package com.example.lakasdekorshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private static final String PREF = SignupActivity.class.getPackage().toString();
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;


    EditText tusername;
    EditText tpassword;
    EditText trepassword;
    EditText temail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tusername = findViewById(R.id.username);
        tpassword = findViewById(R.id.password);
        trepassword = findViewById(R.id.repassword);
        temail = findViewById(R.id.email);

        preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password","");

        temail.setText(email);
        tpassword.setText(password);
        trepassword.setText(password);

        mAuth = FirebaseAuth.getInstance();
    }
    public void signup(View view){
        String username = tusername.getText().toString();
        String password = tpassword.getText().toString();
        String repassword = trepassword.getText().toString();
        String email = temail.getText().toString();

        if(!(password.equals(repassword))){
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("SIGNUP", "CREATED NEW USER");
                    startItems();
                } else {
                    Toast.makeText(SignupActivity.this, "User wasn't created: " + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void cancel(View view){
        finish();
    }
    public void startItems() {
        Intent intent = new Intent(this, ItemListActivity.class);

        startActivity(intent);
    }
}