package com.projectmanagementtoolapp.pkgFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.Role;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgData.Userisinprojectwithrole;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddRoleToUserFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //GUI Attributes
    private TextView title;
    private Spinner spRoles;
    private LinearLayout llRoles;
    private Button btnSave;
    private Button btnCancel;
    private RelativeLayout rl;

    //NON-GUI Attributes
    private Database db;
    private User currentUser;
    private Project currentProject;
    private Userisinprojectwithrole upr;
    //These are for working with the spinner and the linear layout
    private ArrayList<String> allRoles = new ArrayList<>();
    private ArrayList<String> myRoles = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private boolean noRoles = false;
    private int flag = 0;


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
        View view = null;

        try {
            view = inflater.inflate(R.layout.fragment_add_role_to_user, container, false);

            db = Database.getInstance();
            //Get arguments needed
            upr = (Userisinprojectwithrole) this.getArguments().getSerializable("selectedUser");
            currentUser = upr.getUser();
            currentProject = (Project) this.getArguments().getSerializable("currentProject");

            getAllViews(view);
            //Set the text of the label and activity
            getActivity().setTitle("Add roles to " + currentUser.getUsername());
            title.setText(title.getText() + currentUser.getUsername());
            initEventHandlers();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void getAllViews(View view) {
        title = (TextView) view.findViewById(R.id.addRolesToUserTitle);
        spRoles = (Spinner) view.findViewById(R.id.spRoles);
        llRoles = (LinearLayout) view.findViewById(R.id.linearLayoutAddRolesToUser);
        btnSave = (Button) view.findViewById(R.id.btnSaveRoles);
        btnCancel = (Button) view.findViewById(R.id.btnCancelRoles);
        rl = (RelativeLayout) view.findViewById(R.id.rlAddRolesToUser);
    }

    private void initEventHandlers() {
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        spRoles.setOnItemSelectedListener(this);
    }

    public void initSpinner() {
        try {
            if (db.getRoles().size() > 0) {
                allRoles.add("Select a role to add...");
                for (Role role : db.getRoles()) {
                    allRoles.add(role.getName());
                }
            } else {
                allRoles.add("No roles existing yet...");
                spRoles.setEnabled(false);
            }

            adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_spinner_item, allRoles);
            spRoles.setAdapter(adapter);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {

        } else if (v == btnCancel) {
            getActivity().finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            spRoles.getChildAt(0).setEnabled(false);

            if (flag != 0) {

                System.out.println("clicked!");

                if (spRoles.getSelectedItem() != null) {
                    String roleName = (String) spRoles.getSelectedItem();
                    Role selectedRole = db.getRoleByName(roleName);

                    if (!myRoles.contains(roleName)) {
                        myRoles.add(roleName);
                        currentUser.getRolesInProject().get(currentProject).add(selectedRole);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_view_add_contributors, R.id.contributorNameAdd, myRoles);

                        final View view2 = adapter.getView(adapter.getCount() - 1, null, null);
                        final View child = view2.findViewById(R.id.imgDeleteCon);
                        child.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                llRoles.removeView(view2);
                                TextView textView = (TextView) view2.findViewById(R.id.contributorNameAdd);
                                Role role = db.getRoleByName(textView.getText().toString());
                                myRoles.remove(role.getRoleid());
                            }
                        });

                        llRoles.addView(view2);
                    }
                }
            } else {
                flag++;
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}