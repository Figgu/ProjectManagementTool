package com.projectmanagementtoolapp.pkgActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgData.Sprint;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgTasks.InsertRoleTask;
import com.projectmanagementtoolapp.pkgTasks.InsertUserTask;
import com.projectmanagementtoolapp.pkgTasks.SelectAllRolesTask;
import com.projectmanagementtoolapp.pkgTasks.SelectAllUsersTask;
import com.projectmanagementtoolapp.pkgTasks.UpdateUserTask;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Figgu on 10.11.2017.
 */

public class CreateRoleActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnAddRole;
    private Button btnRemoveRole;
    private EditText txtRoleName;
    private EditText txtRoleDescription;
    private CheckBox cbIsUnique;
    private ListView listViewRoles;
    private Database db;

    @Override
    public void onClick(View v) {

        if(v == btnAddRole)
        {

            InsertRoleTask insertRoleTask = new InsertRoleTask(this);
            insertRoleTask.execute(txtRoleName.getText().toString(), txtRoleDescription.getText().toString(), String.valueOf(cbIsUnique.isChecked()));
            try {
                fillListView();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(v == btnRemoveRole)
        {
            InsertRoleTask insertRoleTask = new InsertRoleTask(this);

            insertRoleTask.execute(txtRoleName.getText(), txtRoleDescription.getText(), String.valueOf(cbIsUnique.isChecked()));
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void getAllViews() {
        txtRoleName = (EditText) findViewById(R.id.txt_roleName);
        txtRoleDescription = (EditText) findViewById(R.id.txtRoleDescription);
        cbIsUnique = (CheckBox) findViewById(R.id.cbIsUnique);
        btnAddRole = (Button) findViewById(R.id.btnAddRole);
        btnRemoveRole = (Button) findViewById(R.id.btnRemoveRole);
        listViewRoles = (ListView) findViewById(R.id.listViewRoles);
    }

    private void initEventHandlers() {
        btnAddRole.setOnClickListener(this);
        btnRemoveRole.setOnClickListener(this);
    }

    private void fillListView() throws SQLException, ExecutionException, InterruptedException {
        SelectAllRolesTask selectRolesTask = new SelectAllRolesTask(this);
        selectRolesTask.execute();
        ArrayAdapter<Role> adapter = null;
        String result = selectRolesTask.get();
        adapter = new ArrayAdapter<Role>(this, R.layout.list_view_main, db.getRoles());
        listViewRoles.setAdapter(adapter);
    }


}
