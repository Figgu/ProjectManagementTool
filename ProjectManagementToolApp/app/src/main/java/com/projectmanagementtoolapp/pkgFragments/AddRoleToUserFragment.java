package com.projectmanagementtoolapp.pkgFragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.projectmanagementtoolapp.pkgTasks.UpdateUPRTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AddRoleToUserFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //GUI Attributes
    private TextView title;
    private Spinner spRoles;
    private Button btnCancel;
    private RelativeLayout rl;

    //NON-GUI Attributes
    private Database db;
    private User currentUser;
    private Project currentProject;
    private Userisinprojectwithrole upr;
    //These are for working with the spinner and the linear layout
    private ArrayList<String> allRoles = new ArrayList<>();
    private String myRole = "";
    private ArrayAdapter<String> adapter;
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
        btnCancel = (Button) view.findViewById(R.id.btnCancelRoles);
        rl = (RelativeLayout) view.findViewById(R.id.rlAddRolesToUser);
    }

    private void initEventHandlers() {
        btnCancel.setOnClickListener(this);
        spRoles.setOnItemSelectedListener(this);
    }

    public void initSpinner() {
        try {
            if (db.getRoles().size() != 0) {
                for (Role role : db.getRoles() ) {
                    allRoles.add(role.getName());
                }
            } else {
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
        if (v == btnCancel) {
            getActivity().finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            if (flag != 0) {
                if (spRoles.getSelectedItem() != null) {
                    String roleName = (String) spRoles.getSelectedItem();

                    if (!myRole.equals(roleName)) {
                        myRole = roleName;
                        makeDialog(myRole);
                    }
                }
            } else {
                flag++;
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void makeDialog(String myRole) {
        final String myRoleName = myRole;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add role");
        builder.setMessage("Do you want to add the role '" + myRole + "' to " + currentUser + " ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Userisinprojectwithrole upr = new Userisinprojectwithrole(currentProject, db.getRoleByName(myRoleName), currentUser);
                UpdateUPRTask updateUPRTask = new UpdateUPRTask(getActivity());
                updateUPRTask.execute("upr", upr);
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }
}