package com.projectmanagementtoolapp.pkgActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.projectmanagementtoolapp.R;

public class AddIssueActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtName;
    private EditText txtDescription;
    private EditText txtContributorName;
    private LinearLayout linearLayoutContributors;
    private ImageView imgAdd;
    private Button btnCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        setTitle("Create a new Issue");
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
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getAllViews() {
        txtName = (EditText) findViewById(R.id.txtIssueName);
        txtDescription = (EditText) findViewById(R.id.txtIssueDescription);
        txtContributorName = (EditText) findViewById(R.id.txtContributorNameIssue);
        linearLayoutContributors = (LinearLayout) findViewById(R.id.linearLayoutContributorsIssue);
        imgAdd = (ImageView) findViewById(R.id.imgAddButtonIssue);
        btnCreate = (Button) findViewById(R.id.btnCreateIssue);
    }

    private void initEventHandlers() {
        imgAdd.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}