package com.example.carpetproject;

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

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;
    EditText editTextUserName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextPasswordAgain;

    private FirebaseAuth mAuth;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Bundle bundle = getIntent().getExtras();
        int secret_key = bundle.getInt("SECRET_KEY", 0);

        if (secret_key != 99) {
            finish();
        }
        editTextUserName = findViewById(R.id.userNameEditText);
        editTextEmail = findViewById(R.id.userEmailEditText);
        editTextPassword = findViewById(R.id.userPasswordEditText);
        editTextPasswordAgain = findViewById(R.id.userPasswordAgainEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
        String password = preferences.getString("password", "");

        editTextUserName.setText(userName);
        editTextPassword.setText(password);
        editTextPasswordAgain.setText(password);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG,"onCreate");
    }

    public void register(View view) {
        String userName = editTextUserName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String passwordAgain = editTextPasswordAgain.getText().toString();

        if (!password.equals(passwordAgain)) {
            Log.e(LOG_TAG, "Nem egyező jelszó");
            return;
        }
        Log.i(LOG_TAG, "Regisztrált:" + userName + ", email: " + email);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG,"Sikeresen létrehozott felhasználó");
                    startShopping();
                }
                else{
                    Log.d(LOG_TAG,"Nem sikerült létrehozni a felhasználót");
                    Toast.makeText(RegisterActivity.this, "Nem sikerült létrehozni a felhasználót: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    private void startShopping(){
        Intent shoppingIntent = new Intent(this, ShopListActivity.class);
        //shoppingIntent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(shoppingIntent);
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }
}