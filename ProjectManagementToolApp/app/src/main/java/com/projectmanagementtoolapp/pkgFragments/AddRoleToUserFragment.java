package com.projectmanagementtoolapp.pkgFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgTasks.SelectAllRolesTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddRoleToUserFragment extends Fragment implements View.OnClickListener {

    //GUI Attributes
    private TextView title;
    private Spinner spRoles;
    private LinearLayout llRoles;
    private ImageView imgAdd;
    private Button btnSave;

    //NON-GUI Attributes
    private Database db;
    private User currentUser;
    private Project currentProject;
    //These are for working with the spinner and the linear layout
    private ArrayList<String> allRoles = new ArrayList<>();
    private ArrayList<String> myRoles = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private boolean noRoles = false;


    public AddRoleToUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_role_to_user, container, false);

        db = Database.getInstance();
        //Get arguments needed
        currentUser = (User) this.getArguments().getSerializable("selectedUser");
        currentProject = (Project) this.getArguments().getSerializable("currentProject");

        //Get all roles from db
        SelectAllRolesTask selectRolesTask = new SelectAllRolesTask(getActivity());
        selectRolesTask.execute();
        try {
            String result = selectRolesTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Fill my roles for working with the linear layout
        if (currentUser.getRolesInProject().get(currentProject) != null) {
            for (Role role : currentUser.getRolesInProject().get(currentProject)) {
                myRoles.add(role.getName());
            }
        }


        getAllViews(view);
        //Set the text of the label and activity
        getActivity().setTitle("Add roles to " + currentUser.getUsername());
        title.setText(title.getText() + currentUser.getUsername());
        initEventHandlers();
        initSpinner();

        return view;
    }

    private void getAllViews(View view) {
        title = (TextView) view.findViewById(R.id.addRolesToUserTitle);
        spRoles = (Spinner) view.findViewById(R.id.spRoles);
        llRoles = (LinearLayout) view.findViewById(R.id.linearLayoutAddRolesToUser);
        imgAdd = (ImageView) view.findViewById(R.id.imgAddRoleToUser);
        btnSave = (Button) view.findViewById(R.id.btnSaveRoles);
    }

    private void initEventHandlers() {
        imgAdd.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    private void initSpinner() {
        for (Role role : db.getRoles()) {
            allRoles.add(role.getName());
        }

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, allRoles);
        spRoles.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == imgAdd) {
            String roleName = (String) spRoles.getSelectedItem();
            Role selectedRole = db.getRoleByName(roleName);

            if (currentUser.getRolesInProject().get(currentProject) != null) {
                //Dont adding it more than 1 time
                if (!currentUser.getRolesInProject().get(currentProject).contains(selectedRole)) {
                    myRoles.add(roleName);
                    currentUser.getRolesInProject().get(currentProject).add(selectedRole);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_view_add_contributors, R.id.contributorNameAdd, myRoles);

                    final View view = adapter.getView(spRoles.getSelectedItemPosition(), null, null);
                    final View child = view.findViewById(R.id.imgDeleteCon);
                    child.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            llRoles.removeView(view);
                            TextView textView = (TextView) view.findViewById(R.id.contributorNameAdd);
                            Role role = db.getRoleByName(textView.getText().toString());
                            myRoles.remove(role.getRoleID());
                        }
                    });

                    llRoles.addView(view);
                }
            }
        }
    }
}