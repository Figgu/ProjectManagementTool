package com.projectmanagementtoolapp.pkgActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Gui elements
    private CoordinatorLayout layout;
    private FloatingActionButton fab;
    private ListView projectsList;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;

    //Non gui elements
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Database.getInstance();
        setTitle("Your projects");
        getAllViews();
        setSupportActionBar(toolbar);
        initEventlisteners();
        addFAB();
        addList();
        setUpNavigation();
    }

    private void getAllViews() {
        fab = (FloatingActionButton) findViewById(R.id.fabMain);
        layout = (CoordinatorLayout) findViewById(R.id.layoutMain);
        projectsList = (ListView) findViewById(R.id.projectsList);
        drawer  = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void initEventlisteners() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_showProfile) {

        } else if (id == R.id.nav_logout) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addFAB() {
        fab.setOnClickListener(this);
    }

    private void addList() {
        String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
                "WebOS","Ubuntu","Windows7","Max OS X"};

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.list_view_main, mobileArray);
        projectsList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            Intent intent = new Intent(this, AddProjectActivity.class);
            startActivity(intent);
        }
    }

    public void setUpNavigation() {
        View hView =  navigationView.getHeaderView(0);
        TextView nav_username = (TextView)hView.findViewById(R.id.txtUsernameInNav);
        nav_username.setText(db.getCurrentUser().getUsername());

        TextView nav_email = (TextView)hView.findViewById(R.id.txtEmailInNav);
        nav_email.setText(db.getCurrentUser().getEmail());
    }
}