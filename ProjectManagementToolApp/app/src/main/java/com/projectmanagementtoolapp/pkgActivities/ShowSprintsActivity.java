package com.projectmanagementtoolapp.pkgActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.Sprint;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgData.Userisinprojectwithrole;
import com.projectmanagementtoolapp.pkgFragments.AddRoleToUserFragment;
import com.projectmanagementtoolapp.pkgFragments.AddSprintFragment;
import com.projectmanagementtoolapp.pkgTasks.GetAllRolesTask;
import com.projectmanagementtoolapp.pkgTasks.GetAllSprintsFromProject;
import com.projectmanagementtoolapp.pkgTasks.GetUsersOfProjectTask;

import java.util.ArrayList;
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
    private ArrayList<Sprint> sprints = new ArrayList<>();
    private ArrayList<Userisinprojectwithrole> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_sprints);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

            db = Database.getInstance();
            currentProject = (Project) getIntent().getSerializableExtra("project");
            setTitle("Sprints of " + currentProject);
            getAllViews();
            initEventHandlers();

            GetAllSprintsFromProject getAllSprintsFromProject = new GetAllSprintsFromProject(this);
            getAllSprintsFromProject.execute("projects/sprints", currentProject.getProjectid());
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setSprintFromTask(ArrayList<Sprint> sprints) {
        try {
            this.sprints = sprints;
            currentProject.setSprints(sprints);
            initList();             //Showing sprints
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void setUsersFromTask(ArrayList<Userisinprojectwithrole> users) {
        try {
            this.users = users;
            initList();

            allUsers.setVisible(false);
            allSprints.setVisible(true);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /*
    For back navigation to parent activity
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case android.R.id.home:
                    this.finish();
                    return true;
                case R.id.show_all_users:
                    fab.setVisibility(View.INVISIBLE);
                    showingSprints = false;

                    if (users.size() == 0) {
                        GetUsersOfProjectTask getUsersOfProjectTask = new GetUsersOfProjectTask(this);
                        getUsersOfProjectTask.execute("projects/users", currentProject.getProjectid());
                    } else {
                        initList();
                    }

                    allSprints.setVisible(true);
                    allUsers.setVisible(false);

                    setTitle("Users of " + currentProject);

                    return true;

                case R.id.show_all_sprints:
                    fab.setVisibility(View.VISIBLE);
                    showingSprints = true;
                    setTitle("Sprints of " + currentProject);

                    initList();         //Showing sprints
                    allSprints.setVisible(false);
                    allUsers.setVisible(true);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception ex) {
                Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        return true;
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
        try {
            if (showingSprints) {
                ArrayAdapter<Sprint> adapter = new ArrayAdapter<>(this, R.layout.list_view_sprints, sprints);
                if (sprints.size() > 0) {
                    listSprints.setAdapter(adapter);
                } else {
                    listSprints.setAdapter(null);
                    txtNoSprintsFound.setVisibility(View.VISIBLE);
                }
            } else {
                txtNoSprintsFound.setVisibility(View.INVISIBLE);
                ArrayAdapter<Userisinprojectwithrole> adapter = new ArrayAdapter<>(this, R.layout.list_view_sprints, users);
                listSprints.setAdapter(adapter);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            if (showingSprints) {
                Sprint selectedSprint = (Sprint) listSprints.getItemAtPosition(position);
                Intent intent = new Intent(this, ShowIssuesActivity.class);
                intent.putExtra("sprint", selectedSprint);
                startActivity(intent);
            } else {
                txtNoSprintsFound.setVisibility(View.INVISIBLE);
                listSprints.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);

                Userisinprojectwithrole user = (Userisinprojectwithrole) listSprints.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedUser", user);
                bundle.putSerializable("currentProject", currentProject);
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layoutShowSprints, fragment);
                transaction.commit();
                allSprints.setVisible(false);

                GetAllRolesTask getAllRolesTask = new GetAllRolesTask(this);
                getAllRolesTask.execute("roles");
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private AddRoleToUserFragment fragment = new AddRoleToUserFragment();

    public void fillSpinnerInFragment() {
        fragment.initSpinner();
    }

    @Override
    public void onClick(View v) {
        try {
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

                allUsers.setVisible(false);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}