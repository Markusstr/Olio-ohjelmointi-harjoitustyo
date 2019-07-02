package com.example.foodreview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    Context context;
    EditText loginText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        context = this;

        final Button login, signup;
        final DatabaseManager dbms = DatabaseManager.getInstance(context);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        loginText = findViewById(R.id.usernameEditText);
        passwordText = findViewById(R.id.passwordEditText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if (dbms.searchDatabase(username, password)) {
                    Intent mainActivityIntent = new Intent(LogInActivity.this, MainActivity.class);
                    setResult(RESULT_OK, mainActivityIntent);
                    mainActivityIntent.putExtra("username", username);
                    mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainActivityIntent);
                    finish();
                }
                else {

                    Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.login_failed), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Toast.makeText(this, getResources().getString(R.string.login_usercreated1) + " " + data.getStringExtra("username") + " " + getResources().getString(R.string.login_usercreated2), Toast.LENGTH_SHORT).show();
            }
//            else {
//                Toast.makeText(this, "Cancelled" , Toast.LENGTH_SHORT).show();
//            }
        }
    }

    @Override
    public void onBackPressed() { }

    }
