package com.projectmanagementtoolapp.pkgActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgTasks.InsertRoleTask;
import com.projectmanagementtoolapp.pkgTasks.RemoveRoleTask;
import com.projectmanagementtoolapp.pkgTasks.SelectAllRolesTask;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Figgu on 10.11.2017.
 */

public class CreateRoleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private Button btnAddRole;
    private Button btnRemoveRole;
    private EditText txtRoleName;
    private EditText txtRoleDescription;
    private CheckBox cbIsUnique;
    private ListView listViewRoles;
    private Database db;
    private Role selectedItem;



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
            RemoveRoleTask removeRoleTask = new RemoveRoleTask(this);
            removeRoleTask.execute(String.valueOf(selectedItem.getRoleID()));
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
        listViewRoles.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (Role) listViewRoles.getItemAtPosition(position);
                System.out.println(position);
            }

        });
    }

    private void fillListView() throws SQLException, ExecutionException, InterruptedException {
        SelectAllRolesTask selectRolesTask = new SelectAllRolesTask(this);
        selectRolesTask.execute();
        ArrayAdapter<Role> adapter = null;
        String result = selectRolesTask.get();
        adapter = new ArrayAdapter<Role>(this, R.layout.list_view_sprints, db.getRoles());
        listViewRoles.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        System.out.println("boi");


    }
}
