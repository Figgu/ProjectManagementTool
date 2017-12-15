package com.projectmanagementtoolapp.pkgActivities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgFragments.AddRoleToUserFragment;
import com.projectmanagementtoolapp.pkgFragments.AddSprintFragment;
import com.projectmanagementtoolapp.pkgTasks.SelectAllSprintsTask;
import com.projectmanagementtoolapp.pkgTasks.SelectUsersOfProjectTask;

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
    private MenuItem allUsers;
    private MenuItem allSprints;
    private boolean showingSprints = true;
    private boolean infoShown = false;

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

        initList();             //Showing sprints
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
            case R.id.show_all_users:
                //Display dialog of
                if (!infoShown) {
                    showInfoDialog();
                }

                fab.setVisibility(View.INVISIBLE);
                showingSprints = false;

                SelectUsersOfProjectTask selectUsersOfProjectTask = new SelectUsersOfProjectTask(this);
                selectUsersOfProjectTask.execute(currentProject);

                try {
                    String result = selectUsersOfProjectTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                setTitle("Users of " + currentProject);
                initList();
                allUsers.setVisible(false);
                allSprints.setVisible(true);
                return true;

            case R.id.show_all_sprints:
                fab.setVisibility(View.VISIBLE);
                showingSprints = true;
                setTitle("Sprints of " + currentProject);

                SelectAllSprintsTask selectAllSprintsTask = new SelectAllSprintsTask(this);
                selectAllSprintsTask.execute(currentProject);
                try {
                    String result = selectAllSprintsTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                initList();         //Showing sprints
                allSprints.setVisible(false);
                allUsers.setVisible(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInfoDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set Roles of Users");
        builder.setMessage("You are able to add roles to the user by clicking on his name!")
                .setPositiveButton("Ok cool", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_show_users_of_project, menu);
        allUsers = menu.findItem(R.id.show_all_users);
        allSprints = menu.findItem(R.id.show_all_sprints);
        allSprints.setVisible(false);
        return true;
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
        if (showingSprints) {
            ArrayAdapter<Sprint> adapter = new ArrayAdapter<>(this, R.layout.list_view_sprints, currentProject.getSprints());
            if (currentProject.getSprints().size() > 0) {
                listSprints.setAdapter(adapter);
            } else {
                listSprints.setAdapter(null);
                txtNoSprintsFound.setVisibility(View.VISIBLE);
            }
        } else {
            txtNoSprintsFound.setVisibility(View.INVISIBLE);
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this, R.layout.list_view_sprints, currentProject.getContributors());
            listSprints.setAdapter(adapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (showingSprints) {
            Sprint selectedSprint = (Sprint) listSprints.getItemAtPosition(position);
            Intent intent = new Intent(this, ShowIssuesActivity.class);
            intent.putExtra("sprint", selectedSprint);
            startActivity(intent);
        } else {
            txtNoSprintsFound.setVisibility(View.INVISIBLE);
            listSprints.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.INVISIBLE);

            User user = (User) listSprints.getItemAtPosition(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedUser", user);
            AddRoleToUserFragment fragment = new AddRoleToUserFragment();
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.layoutShowSprints, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            txtNoSprintsFound.setVisibility(View.INVISIBLE);
            listSprints.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.INVISIBLE);

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