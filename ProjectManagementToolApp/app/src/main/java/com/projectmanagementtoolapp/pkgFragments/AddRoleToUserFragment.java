package com.projectmanagementtoolapp.pkgFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;

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
    private HashMap<Integer, String> allRoles = new HashMap<>();
    private HashMap<Integer, String> myRoles = new HashMap<>();


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
        currentUser = (User) this.getArguments().getSerializable("selectedUser");

        getAllViews(view);
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
                allRoles.put(role.getRoleID(), role.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, (ArrayList<String>) allRoles.values());
        spRoles.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v == imgAdd) {
            Role selectedRole = (Role) spRoles.getSelectedItem();

            for (Role role : db.getRoles()) {
                myRoles.put(role.getRoleID(), role.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_view_add_contributors, R.id.contributorNameAdd, (ArrayList<String>) myRoles.values());

            final View view = adapter.getView(adapter.getCount()-1, null, null);
            final View child = view.findViewById(R.id.imgDeleteCon);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llRoles.removeView(view);
                    TextView textView = (TextView) view.findViewById(R.id.contributorNameAdd);
                    Role role = allRoles.get()
                    myRoles.remove(user);
                }
            });


        }
    }
}
