package com.example.foodreview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        Toolbar toolbar = findViewById(R.id.toolbarsettings);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.settings_title));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: call method check password

                boolean errorMessages = false;
                String oldPassword = fieldOldPassword.getText().toString().trim();

                if (!dbms.searchDatabase(username, oldPassword)) {
                    //TODO: Error message when old password was wrong!
                    Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (errorMessages == false) {
                    //TODO: Logic application
                }
            }
        });




        //TODO: Make this activity seem nicer and add an Old password -screen!

    }
}
