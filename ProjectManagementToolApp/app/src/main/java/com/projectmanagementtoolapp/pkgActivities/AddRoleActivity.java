package com.projectmanagementtoolapp.pkgActivities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgTasks.CreateRoleTask;
import com.projectmanagementtoolapp.pkgTasks.GetAllRolesTask;
import com.projectmanagementtoolapp.pkgTasks.DeleteRoleTask;
import com.projectmanagementtoolapp.pkgTasks.UpdateRoleTask;

import java.util.ArrayList;

/**
 * Created by Figgu on 10.11.2017.
 */

public class AddRoleActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    //GUI ELEMENTS
    private EditText txtRoleDescription;
    private ListView listViewRoles;
    private FloatingActionButton fab;
    private RelativeLayout mRoot;

    //NONGUI ELEMENTS
    private Database db;
    private ArrayList<Role> selectedItems;
    private ArrayList<View> selectedViews;
    private MenuItem mDelete;
    private MenuItem mEdit;
    private MenuItem mCancel;

    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_role);

            db = Database.getInstance();
            setTitle("All Roles");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation
            selectedItems = new ArrayList<>();
            selectedViews = new ArrayList<>();

            getAllRoles();

            getAllViews();
            initEventHandlers();
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

                case R.id.delete_roles:
                    try {
                        DeleteRoleTask deleteRoleTask = new DeleteRoleTask(this);
                        deleteRoleTask.execute("roles", selectedItems);
                    } catch (Exception ex) {
                        Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    return true;

                case R.id.cancel_role:
                    selectedItems = new ArrayList<>();

                    for (View view : selectedViews) {
                        view.setBackgroundColor(Color.TRANSPARENT);
                    }

                    for (Role role : selectedItems) {
                        selectedItems.remove(role);
                    }

                    mDelete.setVisible(false);
                    mCancel.setVisible(false);
                    mEdit.setVisible(false);

                    return true;

                case R.id.edit_role:
                    editDialog();

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception ex) {
                Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();

        }

        return true;
    }

    //Also called after update haha
    public void afterDelete() {
        selectedItems = new ArrayList<>();

        for (View view :  selectedViews) {
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        selectedViews = new ArrayList<>();
        mDelete.setVisible(false);
        mEdit.setVisible(false);
        mCancel.setVisible(false);
    }

    private void getAllViews() {
        txtRoleDescription = (EditText) findViewById(R.id.txtRoleDescription);
        listViewRoles = (ListView) findViewById(R.id.listViewRoles);
        fab = (FloatingActionButton) findViewById(R.id.fabAddRole);
        mRoot = (RelativeLayout) findViewById(R.id.layoutAddRole);
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
        mEdit = menu.findItem(R.id.edit_role);
        mCancel = menu.findItem(R.id.cancel_role);
        mEdit.setVisible(false);
        mCancel.setVisible(false);
        mDelete.setVisible(false);
        return true;
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View layout=inflater.inflate(R.layout.dialog_add_role,null);
        final CreateRoleTask createRoleTask = new CreateRoleTask(this);

        builder.setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText txtName = (EditText) layout.findViewById(R.id.txtRoleName);
                        EditText txtDescription = (EditText) layout.findViewById(R.id.txtRoleDescription);
                        CheckBox cbIsUnique = (CheckBox) layout.findViewById(R.id.cbIsUnique);

                        if (txtName.getText().toString().length() > 3 && txtDescription.getText().toString().length() > 3) {
                            Role role = new Role(txtName.getText().toString(), txtDescription.getText().toString(), String.valueOf(cbIsUnique.isChecked()));
                            createRoleTask.execute("roles", role);
                        } else {
                            Snackbar.make(mRoot, "Enter all needed information", Snackbar.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    public void makeSnackbarFromTask(String msg) {
        Snackbar.make(mRoot, msg, Snackbar.LENGTH_LONG).show();
    }

    private void editDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View layout=inflater.inflate(R.layout.dialog_add_role,null);
        final Role role = selectedItems.get(0);
        final UpdateRoleTask updateRoleTask = new UpdateRoleTask(this);

        EditText txtName = (EditText) layout.findViewById(R.id.txtRoleName);
        EditText txtDescription = (EditText) layout.findViewById(R.id.txtRoleDescription);
        CheckBox cbIsUnique = (CheckBox) layout.findViewById(R.id.cbIsUnique);

        txtName.setText(role.getName());
        txtDescription.setText(role.getDescription());
        cbIsUnique.setChecked(Boolean.parseBoolean(role.isUnique()));

        builder.setView(layout)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText txtName = (EditText) layout.findViewById(R.id.txtRoleName);
                        EditText txtDescription = (EditText) layout.findViewById(R.id.txtRoleDescription);
                        CheckBox cbIsUnique = (CheckBox) layout.findViewById(R.id.cbIsUnique);

                        Role newRole = new Role(role.getRoleid(), txtName.getText().toString(), txtDescription.getText().toString(), String.valueOf(cbIsUnique.isChecked()));
                        updateRoleTask.execute("roles", newRole);

                        afterDelete();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    //Only called from async task after it is finished
    public void printSnackbar(String message) {
        Snackbar.make(mRoot, message, Snackbar.LENGTH_LONG).show();
    }

    public void getAllRoles() {
        GetAllRolesTask getAllRolesTask = new GetAllRolesTask(this);
        getAllRolesTask.execute("roles");
    }

    @Override
    public void onClick(View v) {
        if(v == fab) {
            createDialog();
        }
    }

    public void fillListView() {
        ArrayAdapter<Role> adapter = new ArrayAdapter<>(this, R.layout.list_view_add_roles, db.getRoles());
        listViewRoles.setAdapter(adapter);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            selectedItems.add((Role) listViewRoles.getItemAtPosition(position));
            view.setBackgroundColor(Color.LTGRAY);
            selectedViews.add(view);

            mEdit.setVisible(true);

            if (selectedItems.size() > 1)
                mEdit.setVisible(false);

            mDelete.setVisible(true);
            mCancel.setVisible(true);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            selectedItems.remove(listViewRoles.getItemAtPosition(position));
            selectedViews.remove(view);
            view.setBackgroundColor(Color.TRANSPARENT);

            if (selectedItems.size() == 0){
                mDelete.setVisible(false);
                mCancel.setVisible(false);
                mEdit.setVisible(false);
            }

            if (selectedItems.size() < 2 && selectedItems.size() > 0) {
                mEdit.setVisible(true);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
