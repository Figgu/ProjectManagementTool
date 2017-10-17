package com.projectmanagementtoolapp.pkgActivities;

import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.ContributorsAdapter;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {

    //GUI elements
    private EditText txtProjectName;
    private EditText txtProjectDescription;
    private EditText txtContributorName;
    private ImageView imgAddButton;
    private ListView contributorsList;
    private EditText txtProjectStart;
    private Button btnAddProject;
    private RelativeLayout mRoot;

    //Other elements
    private ArrayList<User> contributors;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        contributors = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation
        setTitle("Add new project");
        getAllViews();
        initEventhandlers();
        db = Database.getInstance();
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
        txtProjectName = (EditText) findViewById(R.id.txtProjectName);
        txtProjectDescription = (EditText) findViewById(R.id.txtProjectDescription);
        txtContributorName = (EditText) findViewById(R.id.txtContributorName);
        imgAddButton = (ImageView) findViewById(R.id.imgAddButton);
        contributorsList = (ListView) findViewById(R.id.contributorsList);
        txtProjectStart = (EditText) findViewById(R.id.txtProjectStart);
        btnAddProject = (Button) findViewById(R.id.btnAddProject);
        mRoot = (RelativeLayout) findViewById(R.id.layoutAddProject);
    }

    private void initEventhandlers() {
        imgAddButton.setOnClickListener(this);
        btnAddProject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgAddButton) {
            if (txtContributorName.getText().length() > 1) {
                User user = db.getUserByUsername(txtContributorName.getText().toString());
                if (user != null) {
                    if (!contributors.contains(user)) {
                        contributors.add(db.getUserByUsername(txtContributorName.getText().toString()));
                        System.out.println(contributors);
                        ContributorsAdapter adapter = new ContributorsAdapter(this, contributors);
                        contributorsList.setAdapter(adapter);
                    } else {
                        Snackbar.make(mRoot, "User already in project!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mRoot, "User not found!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(mRoot, "Please add an contributor!", Snackbar.LENGTH_LONG).show();
            }
        } else if (v == btnAddProject) {
            createProject();
        }
    }

    private void createProject() {
        boolean everythingOK = true;

        if (txtProjectName.getText().length() < 3) {
            txtProjectName.setError("Project name too short!");
            everythingOK = false;
        }

        if (txtProjectDescription.getText().length() < 5) {
            txtProjectDescription.setError("Project description too short!");
            everythingOK = false;
        }

        if (txtProjectStart.getText().length() < 1) {
            txtProjectStart.setError("You need to define a start date!");
            everythingOK = false;
        }

        if (contributors.size() < 1) {
            Snackbar.make(mRoot, "You need to add at least one cintributor!", Snackbar.LENGTH_LONG).show();
            everythingOK = false;
        }

        if (everythingOK) {
            contributors.add(db.getCurrentUser());
            Date date = new Date(txtProjectStart.getText().toString());
            Project project = new Project(txtProjectName.getText().toString(), txtProjectDescription.getText().toString(), contributors, date);
        }
    }
}
