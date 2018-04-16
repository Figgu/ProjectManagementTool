package com.projectmanagementtoolapp.pkgActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgTasks.GetMyProjectsTask;
import com.sun.jersey.server.impl.model.parameter.multivalued.ExtractorContainerException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    //Gui elements
    private CoordinatorLayout layout;
    private FloatingActionButton fab;
    private ListView projectsList;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView txtNoProjectsFound;
    //NON-GUI
    private Database db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            setTitle("Your projects");
            getAllViews();
            setSupportActionBar(toolbar);
            initEventlisteners();
            db = Database.getInstance();

            System.out.println("id in main: " + db.getCurrentUser().getUserid());
            GetMyProjectsTask getMyProjectsTask = new GetMyProjectsTask(this);
            getMyProjectsTask.execute("projects/fromUser", db.getCurrentUser().getUserid());

            setUpNavigation();
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    private void getAllViews() {
        fab = (FloatingActionButton) findViewById(R.id.fabMain);
        layout = (CoordinatorLayout) findViewById(R.id.layoutMain);
        projectsList = (ListView) findViewById(R.id.projectsList);
        drawer  = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtNoProjectsFound = (TextView) findViewById(R.id.txtNoProjectsFoundMain);
    }

    private void initEventlisteners() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        projectsList.setOnItemClickListener(this);
        fab.setOnClickListener(this);
        projectsList.setOnItemLongClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showLogoutDialog();
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Logout");
        builder.setMessage("Do you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                logout();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void logout() {
        db.setCurrentUser(null);
        this.finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == R.id.nav_showProfile) {
                Intent intent = new Intent(this, ShowProfileActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                showLogoutDialog();
            } else if (id == R.id.nav_createRole) {
                Intent intent = new Intent(this, AddRoleActivity.class);
                startActivity(intent);
            }

            drawer.closeDrawer(GravityCompat.START);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return true;
    }

    public void addList() {
        try {
            if  (db.getCurrentUser().getProjects().size() > 0) {
                ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_view_main, R.id.projectName, db.getCurrentUser().getProjects());
                projectsList.setAdapter(adapter);
            } else {
                txtNoProjectsFound.setVisibility(View.VISIBLE);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, AddProjectActivity.class);
            startActivity(intent);
        }
    }

    public void setUpNavigation() {
        try {
            View hView =  navigationView.getHeaderView(0);
            TextView nav_username = (TextView)hView.findViewById(R.id.txtUsernameInNav);
            nav_username.setText(db.getCurrentUser().getUsername());

            TextView nav_email = (TextView)hView.findViewById(R.id.txtEmailInNav);
            nav_email.setText(db.getCurrentUser().getEmail());

            ImageView imgNav = (ImageView) hView.findViewById(R.id.imgUserNav);

            Bitmap bmp = BitmapFactory.decodeByteArray(db.getCurrentUser().getProfilepicture(), 0, db.getCurrentUser().getProfilepicture().length);
            imgNav.setImageBitmap(bmp);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Project project = (Project) projectsList.getItemAtPosition(position);
            Intent intent = new Intent(this, ShowSprintsActivity.class);
            intent.putExtra("project", project);
            startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Project project = (Project) projectsList.getItemAtPosition(position);
            System.out.println("---projec: " + project);
            System.out.println("---project users: " + project.getUsers());
            Intent intent = new Intent(this, EditProjectActivity.class);
            intent.putExtra("project", project);
            startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return true;
    }
}