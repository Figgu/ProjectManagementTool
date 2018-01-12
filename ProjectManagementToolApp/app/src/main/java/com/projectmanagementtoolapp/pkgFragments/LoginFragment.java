package com.projectmanagementtoolapp.pkgFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgActivities.ForgotPasswordActivity;
import com.projectmanagementtoolapp.pkgActivities.MainActivity;
import com.projectmanagementtoolapp.pkgData.Controller;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgTasks.CheckLoginTask;
import com.projectmanagementtoolapp.pkgTasks.OpenConnectionTask;
import com.projectmanagementtoolapp.pkgTasks.SelectAllUsersTask;

public class LoginFragment extends Fragment implements View.OnClickListener {

    //GUI elements
    private AutoCompleteTextView txtUsername;
    private EditText txtPassword;
    private TextView txtForogtPw;
    private Button btnLogin;
    private RelativeLayout mRoot;

    //Non gui elements
    private Database db;
    private Controller controller;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        db = Database.getInstance();
        controller = Controller.getInstance();
        getAllViews(view);
        initEventHandlers();

        return view;
    }

    private void getAllViews(View view) {
        txtUsername = (AutoCompleteTextView) view.findViewById(R.id.txtUsernameLogin);
        txtPassword = (EditText) view.findViewById(R.id.txtPasswordLogin);
        txtForogtPw = (TextView) view.findViewById(R.id.txtForogtPwLogin);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        mRoot = (RelativeLayout) view.findViewById(R.id.layoutLogin);
    }

    private void initEventHandlers() {
        btnLogin.setOnClickListener(this);
        txtForogtPw.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            if (view == btnLogin) {
                doLogin();
            } else if(view == txtForogtPw) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    private void closeAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit the Project Management Tool?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);     //Close App
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

    //Check the given account details
    private void doLogin() {
        boolean usernameOK = true;
        boolean passwordOK = true;

        if (txtUsername.getText().length() < 1) {
            usernameOK = false;
            txtUsername.setError("Enter Username!");
        }

        if (txtPassword.getText().length() < 1) {
            passwordOK = false;
            txtPassword.setError("Enter Password!");
        }

        if (usernameOK && passwordOK) {
            User user = new User(txtUsername.getText().toString(), txtPassword.getText().toString());
            System.out.println(user);

            CheckLoginTask checkLoginTask = new CheckLoginTask(getActivity());
            checkLoginTask.execute(user);



            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }
}
