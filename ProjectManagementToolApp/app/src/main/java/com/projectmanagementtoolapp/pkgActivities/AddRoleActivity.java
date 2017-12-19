package com.projectmanagementtoolapp.pkgActivities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgTasks.InsertRoleTask;
import com.projectmanagementtoolapp.pkgTasks.DeleteRoleTask;
import com.projectmanagementtoolapp.pkgTasks.SelectAllRolesTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Figgu on 10.11.2017.
 */

public class AddRoleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    //GUI ELEMENTS
    private EditText txtRoleDescription;
    private ListView listViewRoles;
    private FloatingActionButton fab;

    //NONGUI ELEMENTS
    private Database db;
    private ArrayList<Role> selectedItems;
    private MenuItem mDelete;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_role);

        db = Database.getInstance();
        setTitle("All Roles");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation
        selectedItems = new ArrayList<>();

        getAllViews();
        initEventHandlers();
        fillListView();
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

            case R.id.delete_roles:
                DeleteRoleTask deleteRoleTask = null;

                for (Role role : selectedItems) {
                    deleteRoleTask = new DeleteRoleTask(this);
                    deleteRoleTask.execute(role);
                    selectedItems.remove(role);
                }

                try {
                    String result = deleteRoleTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                fillListView();

                if (selectedItems.size() == 0)
                    mDelete.setVisible(false);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getAllViews() {
        txtRoleDescription = (EditText) findViewById(R.id.txtRoleDescription);
        listViewRoles = (ListView) findViewById(R.id.listViewRoles);
        fab = (FloatingActionButton) findViewById(R.id.fabAddRole);
    }

    private void initEventHandlers() {
        listViewRoles.setOnItemClickListener(this);
        listViewRoles.setOnItemLongClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_add_roles, menu);
        mDelete = menu.findItem(R.id.delete_roles);
        mDelete.setVisible(false);
        return true;
    }


    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View layout=inflater.inflate(R.layout.dialog_add_role,null);

        builder.setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText txtName = (EditText) layout.findViewById(R.id.txtRoleName);
                        EditText txtDescription = (EditText) layout.findViewById(R.id.txtRoleDescription);
                        CheckBox cbIsUnique = (CheckBox) layout.findViewById(R.id.cbIsUnique);

                        Role role = new Role(txtName.getText().toString(), txtDescription.getText().toString(), cbIsUnique.isChecked());
                        insertRole(role);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    private void insertRole(Role role) {
        InsertRoleTask insertRoleTask = new InsertRoleTask(this);
        insertRoleTask.execute(role);
        fillListView();
    }

    @Override
    public void onClick(View v) {
        if(v == fab) {
            createDialog();
        }
    }

    private void fillListView() {
        SelectAllRolesTask selectRolesTask = new SelectAllRolesTask(this);
        selectRolesTask.execute();
        try {
            String result = selectRolesTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayAdapter<Role> adapter = new ArrayAdapter<>(this, R.layout.list_view_add_roles, db.getRoles());
        listViewRoles.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectedItems.add((Role) listViewRoles.getItemAtPosition(position));
        view.setBackgroundColor(Color.LTGRAY);
        mDelete.setVisible(true);

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedItems.remove(listViewRoles.getItemAtPosition(position));
        view.setBackgroundColor(Color.TRANSPARENT);

        if (selectedItems.size() == 0)
            mDelete.setVisible(false);
    }
}
