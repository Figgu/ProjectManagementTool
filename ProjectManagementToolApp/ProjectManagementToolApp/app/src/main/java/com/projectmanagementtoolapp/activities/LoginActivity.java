package com.projectmanagementtoolapp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView txtUsername;
    private EditText txtPassword;
    private TextView txtRegister;
    private TextView txtForogtPw;
    private Button btnLogin;
    private RelativeLayout mRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");
        getAllViews();
        initEventHandlers();
    }

    private void getAllViews() {
        txtUsername = (AutoCompleteTextView) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtForogtPw = (TextView) findViewById(R.id.txtForogtPw);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        mRoot = (RelativeLayout) findViewById(R.id.layoutLogin);
    }

    private void initEventHandlers() {
        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForogtPw.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            if (view.equals(btnLogin)) {
                checkInput();

            } else if(view == txtForogtPw) {
                Toast.makeText(this, "Forgot pw works", Toast.LENGTH_SHORT).show();
            } else if(view == txtRegister) {
                Toast.makeText(this, "register works", Toast.LENGTH_SHORT);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    //Check the given account details
    private void checkInput() {
        boolean usernameOK = true;
        boolean passwordOK = true;

        if (txtUsername.getText().length() < 1) {
            usernameOK = false;
            txtUsername.setError("Enter Username!");
        }

        if (txtPassword.getText().length() < 1) {
            passwordOK = false;
            txtPassword.setError("Enter Password!");
        }

        if (usernameOK && passwordOK) {
            Snackbar.make(mRoot, "Everything ok", Snackbar.LENGTH_SHORT).show();
        }
    }
}

