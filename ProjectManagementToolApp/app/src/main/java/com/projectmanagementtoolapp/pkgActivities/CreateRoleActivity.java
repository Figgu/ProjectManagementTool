package com.projectmanagementtoolapp.pkgActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgData.Sprint;
import com.projectmanagementtoolapp.pkgData.User;

import java.sql.SQLException;

/**
 * Created by Figgu on 10.11.2017.
 */

public class CreateRoleActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAddRole;
    private Button btnRemoveRole;
    private EditText txtRoleName;
    private ListView listViewRoles;
    private Database db;

    @Override
    public void onClick(View v) {

        if(v == btnAddRole)
        {

        }
        else if(v == btnRemoveRole)
        {

        }

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createrole);
        db = Database.getInstance();
        setTitle("Roles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        getAllViews();
        initEventHandlers();

        try {
            fillListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getAllViews() {
        txtRoleName = (EditText) findViewById(R.id.txt_roleName);
        btnAddRole = (Button) findViewById(R.id.btnAddRole);
        btnRemoveRole = (Button) findViewById(R.id.btnRemoveRole);
        listViewRoles = (ListView) findViewById(R.id.listViewRoles);
    }

    private void initEventHandlers() {
        btnAddRole.setOnClickListener(this);
        btnRemoveRole.setOnClickListener(this);
    }

    private void fillListView() throws SQLException {
        ArrayAdapter<Role> adapter = new ArrayAdapter<Role>(this, R.layout.list_view_main, db.getAllRoles());
        listViewRoles.setAdapter(adapter);
    }


}
