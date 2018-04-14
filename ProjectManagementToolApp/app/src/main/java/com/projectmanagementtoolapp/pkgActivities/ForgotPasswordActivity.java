package com.projectmanagementtoolapp.pkgActivities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.User;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    //GUI elements
    private EditText txtEmail;
    private Button btnSendEmail;

    //non gui elements
    private Database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        setTitle("Forgot password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        getAllViews();
        initEventHandlers();
        db = Database.getInstance();
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
        /*
        if (v == btnSendEmail) {
            if(isEmailValid()) {
                sendEmail(db.getUserByEmail(txtEmail.getText().toString()));
            } else {
                txtEmail.setError("Email not valid");
            }
        }
        */
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

    public void sendEmail(User user) {
        StringBuilder builder = new StringBuilder();
        builder.append("Your account details for the project management tool");
        builder.append("Username: " + user.getUsername());
        builder.append("Password: " + user.getPassword());
        builder.append("This is an automaticaly sent email, do not reply");
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{user.getEmail()});
        i.putExtra(Intent.EXTRA_SUBJECT, "PMT: Account Details");
        i.putExtra(Intent.EXTRA_TEXT   , builder.toString());
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ForgotPasswordActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}