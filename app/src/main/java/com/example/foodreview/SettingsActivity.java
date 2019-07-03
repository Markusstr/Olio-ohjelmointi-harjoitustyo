package com.example.foodreview;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    TextView fieldUsername;
    EditText fieldOldPassword, fieldNewPassword,fieldNewPasswordAgain;
    Button changePassword;
    DatabaseManager dbms;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Set the username from another activity
        fieldUsername = findViewById(R.id.fieldUsername);
        final String username = getIntent().getStringExtra("username");
        fieldUsername.setText(username);

        fieldOldPassword = findViewById(R.id.oldPassword);
        fieldNewPassword = findViewById(R.id.newPassword);
        fieldNewPasswordAgain = findViewById(R.id.newPasswordAgain);
        changePassword = findViewById(R.id.changePassword);
        context = this;
        dbms = DatabaseManager.getInstance(context);
        final PasswordChecker pwc = PasswordChecker.getInstance(context);

        Toolbar toolbar = findViewById(R.id.toolbarsettings);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.settings_title));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (dbms.isAdmin(username)) {
            Toast.makeText(context, "Admin on totta.", Toast.LENGTH_SHORT).show();
        }
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: call method check password
                String oldPassword = fieldOldPassword.getText().toString().trim();

                //TODO: Check if these passwords are the same
                String newPassword = fieldNewPassword.getText().toString().trim();
                String newPasswordAgain = fieldNewPasswordAgain.getText().toString().trim();

                if (!dbms.searchDatabase(username, oldPassword)) {
                    System.out.println(username + oldPassword);
                    //TODO: Error message when old password was wrong!
                    Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (pwc.checker(newPassword).isEmpty()) {
                        if (!dbms.changePassword(username, newPassword)) {
                            System.out.println("Oops! Something went wrong with saving the password. Try again in a few minutes");
                        }
                        else {
                            InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                            fieldOldPassword.setText("");
                            fieldNewPassword.setText("");
                            fieldNewPasswordAgain.setText("");
                            Snackbar.make(v, "Password changed successfully!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });




        //TODO: Make this activity seem nicer and add an Old password -screen!

    }
}
