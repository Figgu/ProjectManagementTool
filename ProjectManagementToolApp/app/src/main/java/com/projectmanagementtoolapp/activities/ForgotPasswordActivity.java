package com.projectmanagementtoolapp.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.projectmanagementtoolapp.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail;
    private Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setTitle("Forgot password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        getAllViews();
        initEventHandlers();
    }

    private void getAllViews() {
        txtEmail = (EditText) findViewById(R.id.txtEmailForgotPw);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
    }

    private void initEventHandlers() {
        btnSendEmail.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v == btnSendEmail) {
            if(isEmailValid()) {
                showDialog();
            } else {
                txtEmail.setError("Email not valid");
            }
        }
    }

    private boolean isEmailValid() {
        boolean isValid = false;

        if (txtEmail.getText().length() > 3) {
            if (txtEmail.getText().toString().contains("@")) {
                isValid = true;
            }
        }

        return isValid;
    }

    private void showDialog() {
        final Activity ac = this;
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("An email with your account details has been sent to your email address!");
        dlgAlert.setTitle("E-Mail sent!");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ac.finish();        //Close activity
            }
        });
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }
}