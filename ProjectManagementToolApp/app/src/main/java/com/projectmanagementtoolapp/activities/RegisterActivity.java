package com.projectmanagementtoolapp.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.projectmanagementtoolapp.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnRegister;
    private RelativeLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation
        getAllViews();
        initEventHandlers();
    }

    /*
    For back navigation to parent activity
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getAllViews() {
        txtUsername = (EditText) findViewById(R.id.txtUsernameRegister);
        txtEmail = (EditText) findViewById(R.id.txtEmailRegister);
        txtPassword = (EditText) findViewById(R.id.txtPasswordRegister);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPasswordRegister);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        mRoot = (RelativeLayout) findViewById(R.id.layoutRegister);
    }

    private void initEventHandlers() {
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnRegister) {
            doRegister();
        }
    }

    //Checking the given account details
    private void doRegister() {
        boolean usernameEntered = true;
        boolean emailEntered = true;
        boolean passwordEntered = true;
        boolean passwordConfirmedEntered = true;

        boolean usernameOK = true;
        boolean emailOK =true;
        boolean passwordOK =true;

        if (txtUsername.getText().length() < 1) {
            usernameEntered = false;
            txtUsername.setError("Enter Username!");
        }

        if (txtEmail.getText().length() < 1) {
            usernameEntered = false;
            txtEmail.setError("Enter Email!");
        }

        if (txtPassword.getText().length() < 1) {
            passwordEntered = false;
            txtPassword.setError("Enter Password!");
        }

        if (txtConfirmPassword.getText().length() < 1) {
            passwordConfirmedEntered = false;
            txtConfirmPassword.setError("You need to confirm your Password!");
        }

        //Check if everything got entered
        if (usernameEntered && emailEntered && passwordEntered && passwordConfirmedEntered) {
            //Check if the passwords are equal
            if (txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                //Check if they given details are ok
                if (txtUsername.getText().length() < 3) {
                    usernameOK = false;
                    txtUsername.setError("Username needs to be at least 3 characters long!");
                }

                if (!txtEmail.getText().toString().contains("@") || txtEmail.getText().length() < 3) {
                    emailOK = false;
                    txtEmail.setError("Email address not valid!");
                }
                if (txtPassword.getText().length() < 3) {
                    passwordOK = false;
                    txtPassword.setError("Password needs to be at least 3 characters long!");
                }

                if (usernameOK && emailOK && passwordOK) {
                    //TODO register
                }
            } else {
                txtPassword.setText("");
                txtConfirmPassword.setText("");
                Snackbar.make(mRoot, "Your passwords are not equal!", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}