package com.projectmanagementtoolapp.pkgActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.projectmanagementtoolapp.R;

import java.util.ArrayList;
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

    //Other elements
    private List contributors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        setTitle("Add new project");
        getAllViews();
        initEventhandlers();
    }

    private void getAllViews() {
        txtProjectName = (EditText) findViewById(R.id.txtProjectName);
        txtProjectDescription = (EditText) findViewById(R.id.txtProjectDescription);
        txtContributorName = (EditText) findViewById(R.id.txtContributorName);
        imgAddButton = (ImageView) findViewById(R.id.imgAddButton);
        contributorsList = (ListView) findViewById(R.id.contributorsList);
        txtProjectStart = (EditText) findViewById(R.id.txtProjectStart);
        btnAddProject = (Button) findViewById(R.id.btnAddProject);
    }

    private void initEventhandlers() {
        imgAddButton.setOnClickListener(this);
        btnAddProject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgAddButton) {
            if (txtContributorName.getText().length() > 1) {
                contributors.add(txtContributorName.getText().toString());
                contributorsList.setAdapter(null);
                ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.listview, contributors);
                contributorsList.setAdapter(adapter);
            } else {
                txtContributorName.setError("Name not long enough");
            }
        } else if (v == btnAddProject) {

        }
    }
}
