package com.projectmanagementtoolapp.pkgActivities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.Sprint;
import com.projectmanagementtoolapp.pkgFragments.AddSprintFragment;
import com.projectmanagementtoolapp.pkgTasks.SelectAllSprintsTask;

import java.util.concurrent.ExecutionException;

public class ShowSprintsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    //Gui elements
    private ListView listSprints;
    private FloatingActionButton fab;
    private TextView txtNoSprintsFound;
    private RelativeLayout layoutShowSprints;

    //Non gui elements
    private Database db;
    private Project currentProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sprints);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        db = Database.getInstance();
        currentProject = (Project) getIntent().getSerializableExtra("project");
        setTitle("Sprints of " + currentProject);
        System.out.println("currentProject: " +currentProject.getSprints());
        getAllViews();
        initEventHandlers();

        SelectAllSprintsTask selectAllSprintsTask = new SelectAllSprintsTask(this);
        selectAllSprintsTask.execute(currentProject);
        try {
            String result = selectAllSprintsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("currentProject: " +currentProject.getSprints());

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
        listSprints = (ListView) findViewById(R.id.listSprints);
        fab = (FloatingActionButton) findViewById(R.id.fabShowSprints);
        txtNoSprintsFound = (TextView) findViewById(R.id.txtNoSprintsFound);
        layoutShowSprints = (RelativeLayout) findViewById(R.id.layoutShowSprints);
    }

    private void initEventHandlers() {
        listSprints.setOnItemClickListener(this);
        fab.setOnClickListener(this);
    }

    private void initList() {
        if (currentProject.getSprints().size() > 0) {
            ArrayAdapter<Sprint> adapter = new ArrayAdapter<>(this, R.layout.list_view_sprints, currentProject.getSprints());
            listSprints.setAdapter(adapter);
        } else {
            txtNoSprintsFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Sprint selectedSprint = (Sprint) listSprints.getItemAtPosition(position);
        Intent intent = new Intent(this, ShowIssuesActivity.class);
        intent.putExtra("sprint", selectedSprint);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            txtNoSprintsFound.setVisibility(View.INVISIBLE);
            listSprints.setVisibility(View.INVISIBLE);

            Bundle bundle = new Bundle();
            bundle.putSerializable("currentProject", currentProject);
            AddSprintFragment fragment = new AddSprintFragment();
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.layoutShowSprints, fragment);
            transaction.commit();
        }
    }
}