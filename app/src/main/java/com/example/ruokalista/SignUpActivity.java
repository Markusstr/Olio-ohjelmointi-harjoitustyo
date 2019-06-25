package com.example.ruokalista;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    private EditText username, password, passwordagain;
    private Button create, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordagain = findViewById(R.id.passwordagain);

        create = findViewById(R.id.create);
        cancel = findViewById(R.id.cancel);


        passwordagain.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String message = passwordCheck();
                //Let's check if we get any error messages from passwordCheck
                //If we don't, the password fulfills the requirements and we don't need to add any text listeners to the first editText
                //If the password is not ok, we make the line red + add error drawable to indicate that there is an error in the password
                if (!message.isEmpty()) {
                    password.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.error));
                    password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_error, 0);

                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (passwordCheck().isEmpty()) {
                                password.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.colorAccent));
                                password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0,0, 0);
                            }
                            else {
                                password.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.error));
                                password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_error, 0);
                            }
                        }
                    });
                }
                else {

                    if (passwordagain.getText().toString().equals(password.getText().toString())) {
                        passwordagain.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.colorAccent));
                        passwordagain.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0,0, 0);
                    }
                    else {
                        passwordagain.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.error));
                        passwordagain.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_error, 0);
                    }

                    passwordagain.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (passwordagain.getText().toString().equals(password.getText().toString())) {
                                passwordagain.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.colorAccent));
                                passwordagain.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0,0, 0);
                            }
                            else {
                                passwordagain.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.error));
                                passwordagain.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock, 0, R.drawable.ic_error, 0);
                            }
                        }
                    });
                }
            }
        });



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if the password fulfills all the requirements
                String message = passwordCheck();
                //Print the message to user if it's not empty
                if (!message.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else if (!passwordagain.getText().toString().equals(password.getText().toString())) {
                    //Toast.makeText(SignUpActivity.this, "Salasanat eivät ole samat", Toast.LENGTH_SHORT).show();
                }
                else if (username.getText().toString().length() < 6) {
                    username.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.error));
                    username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person, 0, R.drawable.ic_error, 0);
                    username.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (username.getText().toString().length() < 6) {
                                username.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.error));
                                username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person, 0, R.drawable.ic_error, 0);

                            }
                            else {
                                username.setBackgroundTintList(ContextCompat.getColorStateList(SignUpActivity.this, R.color.colorAccent));
                                username.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person, 0,0, 0);
                            }

                        }
                    });
                }
                //If the password fulfills all the requirements (error message is empty), we will create new user
                else {
                    //TODO: Check if user with this username is already created
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivityCheck();
            }
        });
    }

    @Override
    public void onBackPressed() {
        closeActivityCheck();
    }


    //If user has typed something in the text fields and tries to leave, alert dialog shows up
    private void closeActivityCheck() {
        if (!username.getText().toString().equals("") || !password.getText().toString().equals("") || !passwordagain.getText().toString().equals("")) {
            new AlertDialog.Builder(SignUpActivity.this)
                    .setTitle(R.string.signup_alertdialog_notsaved)
                    .setMessage(R.string.signup_alertdialog_confirm)
                    .setPositiveButton(R.string.signup_alertdialog_exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //onClickListener if user clicks exit
                            finish();
                        }
                    })
                    //Keep editing button doesn't need onClickListener because it just goes back to the activity
                    .setNegativeButton(R.string.signup_alertdialog_keepediting, null)
                    .setIcon(R.drawable.ic_warning)
                    .show();
        }
        else {
            finish();
        }
    }

    private String passwordCheck() {
        String message = "";
        if (password.getText().toString().length() < 12) {
            message = message.concat(getResources().getString(R.string.signup_password_tooshort) + "\n");
        }
        /*if (password.getText().toString().length() > 32) {
            message = message.concat(getResources().getString(R.string.//TODO: signup_password_toolong));
        }*/
        if (password.getText().toString().equals(password.getText().toString().toLowerCase())) {
            message = message.concat(getResources().getString(R.string.signup_password_uppercase) + "\n");
        }
        if (password.getText().toString().equals(password.getText().toString().toUpperCase())) {
            message = message.concat(getResources().getString(R.string.signup_password_lowercase) + "\n");
        }
        //TODO: Check if password contains special letter

        return message;
    }

}
