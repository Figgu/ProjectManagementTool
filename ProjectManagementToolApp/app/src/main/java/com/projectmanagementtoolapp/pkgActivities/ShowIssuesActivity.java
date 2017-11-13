package com.projectmanagementtoolapp.pkgActivities;

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

public class ShowIssuesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Gui elements
    private ListView listIssues;
    private FloatingActionButton fab;
    private TextView txtNoIssuesFound;
    private RelativeLayout layoutShowIssues;

    //Non gui elements
    private Database db;
    private Sprint currentSprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_issues);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        db = Database.getInstance();
        currentSprint = (Sprint) getIntent().getSerializableExtra("sprint");
        setTitle("Issues of Sprint");
        getAllViews();
        initEventHandlers();
        initList();
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

    private void initList() {
        if (currentSprint.getIssues().size() > 1) {
            ArrayAdapter<Issue> adapter = new ArrayAdapter<>(this, R.layout.activity_show_issues, currentSprint.getIssues());
            listIssues.setAdapter(adapter);
        } else {
            txtNoIssuesFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {

    }
}
