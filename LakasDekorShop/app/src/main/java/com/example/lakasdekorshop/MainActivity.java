package com.example.lakasdekorshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String PREF = MainActivity.class.getPackage().toString();
    private SharedPreferences preferences;
    private FirebaseAuth mAuth;
//    private GoogleSignInClient mGoogle;

    EditText Textemail;
    EditText Textpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Textemail = findViewById(R.id.email);
        Textpassword = findViewById(R.id.password);

        preferences = getSharedPreferences(PREF, MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
//        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("email", Textemail.getText().toString());
        editor.putString("password", Textpassword.getText().toString());
        editor.apply();
    }

    public void signup(View view){
        Intent intent = new Intent(this,SignupActivity.class);

        startActivity(intent);
    }

    public void login(View view){
        String email = Textemail.getText().toString();
        String password = Textpassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startItems();
                } else {
                    Toast.makeText(MainActivity.this, "Hiba: "+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void startItems() {
        Intent intent = new Intent(this, ItemListActivity.class);

        startActivity(intent);
    }

//    public void loginGoogle(View view){
//        String email = Textemail.getText().toString();
//        String password = Textpassword.getText().toString();
//
//    }
}