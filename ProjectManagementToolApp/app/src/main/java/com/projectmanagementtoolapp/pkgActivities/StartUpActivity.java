package com.projectmanagementtoolapp.pkgActivities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.ViewPagerAdapter;
import com.projectmanagementtoolapp.pkgFragments.LoginFragment;
import com.projectmanagementtoolapp.pkgFragments.RegisterFragment;

public class StartUpActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private RelativeLayout mRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);

        setTitle("Project Management Tool");

        mRoot = (RelativeLayout) findViewById(R.id.rlStartUp);
        toolbar = (Toolbar) findViewById(R.id.toolbarStartUp);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*
    Add Tabs(Fragments) to Activity
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new RegisterFragment(), "Register");
        viewPager.setAdapter(adapter);
    }


    //Is Called after the check login task is finished
    public void loginResult(boolean isOk, String message) {
        if (isOk) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(mRoot, message, Snackbar.LENGTH_LONG).show();
        }
    }

    //Is Called after the check login task is finished
    public void createResult(String message) {
        Snackbar.make(mRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
