package com.projectmanagementtoolapp.pkgActivities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Issue;
import com.projectmanagementtoolapp.pkgData.IssueStatus;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.Sprint;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgData.Userisinissue;
import com.projectmanagementtoolapp.pkgTasks.AddUserToIssueTask;
import com.projectmanagementtoolapp.pkgTasks.CreateIssueTask;
import com.projectmanagementtoolapp.pkgTasks.GetAllUsersTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddIssueActivity extends AppCompatActivity implements View.OnClickListener {

    //GUI-Attributes
    private EditText txtName;
    private EditText txtDescription;
    private EditText txtContributorName;
    private LinearLayout linearLayout;
    private ImageView imgAdd;
    private Button btnCreate;
    private RelativeLayout mRoot;

    //NON-GUI-Attributes
    private ArrayList<User> contributors;
    private ArrayAdapter<User> adapter;
    private Database db;
    private Sprint currentSprint;
    private Project currentProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_issue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        setTitle("Create a new Issue");
        getAllViews();
        initEventHandlers();
        db = Database.getInstance();
        contributors = new ArrayList<>();
        currentSprint = (Sprint) getIntent().getSerializableExtra("currentSprint");
        currentProject = (Project) getIntent().getSerializableExtra("currentProject");

        GetAllUsersTask getAllUsersTask = new GetAllUsersTask(this);
        getAllUsersTask.execute("users");
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
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutContributorsIssue);
        imgAdd = (ImageView) findViewById(R.id.imgAddButtonIssue);
        btnCreate = (Button) findViewById(R.id.btnCreateIssue);
        mRoot = (RelativeLayout) findViewById(R.id.relAddIssue);
    }

    private void initEventHandlers() {
        imgAdd.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgAdd) {
            if (txtContributorName.getText().length() > 1) {
                String enteredUsername = txtContributorName.getText().toString();
                User user = db.getUserByName(enteredUsername);

                if (db.getUsers().contains(user)) {
                    if (!contributors.contains(user)) {
                        contributors.add(user);
                        System.out.println(contributors);
                        adapter = new ArrayAdapter<>(this, R.layout.list_view_add_contributors, R.id.contributorNameAdd, contributors);
                        final View view = adapter.getView(adapter.getCount()-1, null, null);
                        final View child = view.findViewById(R.id.imgDeleteCon);
                        child.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                linearLayout.removeView(view);
                                TextView textView = (TextView) view.findViewById(R.id.contributorNameAdd);
                                User user = db.getUserByName(textView.getText().toString());
                                contributors.remove(user);
                            }
                        });

                        linearLayout.addView(view);
                        txtContributorName.setText("");
                    } else {
                        Snackbar.make(mRoot, "User already in issue!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mRoot, "User not found!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(mRoot, "Input not valid!", Snackbar.LENGTH_LONG).show();
            }
        } else if (v == btnCreate) {
            try {
                createIssue();
            } catch (ExecutionException e) {
                Snackbar.make(mRoot, e.getMessage(), Snackbar.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                Snackbar.make(mRoot, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public void makeSnackbar(String message) {
        Snackbar.make(mRoot, message, Snackbar.LENGTH_LONG).show();
    }

    private void createIssue() throws ExecutionException, InterruptedException {
        boolean ok = true;

        if (txtName.getText().length() < 1) {
            txtName.setError("Enter a name");
            ok = false;
        }

        if (txtDescription.getText().length() < 4) {
            txtName.setError("Enter a longer description");
            ok = false;
        }

        if (txtName.getText().length() < 1) {
            txtName.setError("Enter a name");
            ok = false;
        }

        if (ok) {
            Issue issue = new Issue(txtName.getText().toString(), txtDescription.getText().toString(), IssueStatus.TODO.toString(), currentSprint);
            CreateIssueTask createIssueTask = new CreateIssueTask(this);
            createIssueTask.execute("issues/" + currentProject.getProjectid(), issue);
            createIssueTask.get();
        }
    }

    public void addUsers(String issueStr) {
        Issue issue = new Gson().fromJson(issueStr, Issue.class);
        ArrayList<Userisinissue> users = new ArrayList<>();

        for (User user : contributors) {
            users.add(new Userisinissue(issue, user));
        }

        AddUserToIssueTask addUserToIssueTask = new AddUserToIssueTask(this);
        addUserToIssueTask.execute("uii", users);
    }
}