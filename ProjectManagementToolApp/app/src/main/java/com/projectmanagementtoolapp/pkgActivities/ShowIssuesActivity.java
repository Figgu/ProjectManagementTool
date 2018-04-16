package com.projectmanagementtoolapp.pkgActivities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Issue;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.Sprint;
import com.projectmanagementtoolapp.pkgFragments.ShowIssueFragment;
import com.projectmanagementtoolapp.pkgTasks.GetAllIssuesTask;
import com.projectmanagementtoolapp.pkgTasks.GetAllRolesTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ShowIssuesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Gui elements
    private ListView listIssues;
    private FloatingActionButton fab;
    private TextView txtNoIssuesFound;
    private RelativeLayout layoutShowIssues;

    //Non gui elements
    private Database db;
    private Sprint currentSprint;
    private Project currentProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_issues);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        db = Database.getInstance();
        currentSprint = (Sprint) getIntent().getSerializableExtra("sprint");
        currentProject = (Project) getIntent().getSerializableExtra("project");
        setTitle("Issues of " + currentSprint);
        getAllViews();
        initEventHandlers();

        GetAllIssuesTask getAllIssuesTask = new GetAllIssuesTask(this);
        getAllIssuesTask.execute("sprints/allIssues/"+currentSprint.getSprintID());
        try {
            getAllIssuesTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        getAllIssuesTask = new GetAllIssuesTask(this);
        getAllIssuesTask.execute("sprints/allIssues/"+currentSprint.getSprintID());
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
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
        listIssues = (ListView) findViewById(R.id.listIssues);
        fab = (FloatingActionButton) findViewById(R.id.fabShowIssues);
        txtNoIssuesFound = (TextView) findViewById(R.id.txtNoIssuesFound);
        layoutShowIssues = (RelativeLayout) findViewById(R.id.layoutShowIssues);
    }

    private void initEventHandlers() {
        listIssues.setOnItemClickListener(this);
        fab.setOnClickListener(this);
    }

    public void initList(ArrayList<Issue> issues) {
        if (issues.size() != 0) {
            ArrayAdapter<Issue> adapter = new ArrayAdapter<>(this, R.layout.list_view_issues, issues);
            listIssues.setAdapter(adapter);
            txtNoIssuesFound.setVisibility(View.INVISIBLE);

        } else {
            txtNoIssuesFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Issue issue = (Issue) listIssues.getItemAtPosition(position);
        listIssues.setVisibility(View.INVISIBLE);
        ShowIssueFragment showIssueFragment = new ShowIssueFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("issue", issue);
        showIssueFragment.setArguments(bundle);
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layoutShowIssues, showIssueFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, AddIssueActivity.class);
            intent.putExtra("currentSprint", currentSprint);
            intent.putExtra("currentProject", currentProject);
            startActivity(intent);
        }
    }
}
