package com.projectmanagementtoolapp.pkgActivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgTasks.OpenConnectionTask;
import com.projectmanagementtoolapp.pkgTasks.SelectAllUsersTask;
import com.projectmanagementtoolapp.pkgData.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //GUI elements
    private AutoCompleteTextView txtUsername;
    private EditText txtPassword;
    private TextView txtRegister;
    private TextView txtForogtPw;
    private Button btnLogin;
    private RelativeLayout mRoot;
    private ProgressDialog pd;

    //Non gui elements
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = Database.getInstance();
        setTitle("Login");
        getAllViews();
        initEventHandlers();

        OpenConnectionTask openConnectionTask = new OpenConnectionTask();
        openConnectionTask.execute();

        SelectAllUsersTask selectAllUsersTask = new SelectAllUsersTask(this);
        selectAllUsersTask.execute();
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
        User user = null;
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
            user = db.getUserByUsername(txtUsername.getText().toString());
            System.out.println(user);

            if (user != null) {
                if (user.getUsername().equals(txtUsername.getText().toString()) && user.getPassword().equals(txtPassword.getText().toString())) {
                    db.setCurrentUser(user);
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mRoot, "Password wrong", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(mRoot, "Username not correct", Snackbar.LENGTH_LONG).show();
            }

        }
    }
}

