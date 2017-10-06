package com.projectmanagementtoolapp.activities;

import android.content.Intent;
import android.os.Bundle;
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
        txtUsername = (AutoCompleteTextView) findViewById(R.id.txtUsernameLogin);
        txtPassword = (EditText) findViewById(R.id.txtPasswordLogin);
        txtRegister = (TextView) findViewById(R.id.txtRegisterLogin);
        txtForogtPw = (TextView) findViewById(R.id.txtForogtPwLogin);
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
            if (view == btnLogin) {
                doLogin();
            } else if(view == txtForogtPw) {
                Intent intent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(intent);
            } else if(view == txtRegister) {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    //Check the given account details
    private void doLogin() {
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
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

