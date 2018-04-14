package com.projectmanagementtoolapp.pkgFragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgTasks.CreateUserTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutionException;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    //Gui elements
    private EditText txtUsername;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnRegister;
    private RelativeLayout mRoot;

    //Other Attributes
    private Database db = null;

    public RegisterFragment() {
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
        View view =  null;

        try {
            view = inflater.inflate(R.layout.fragment_register, container, false);

            getAllViews(view);
            initEventHandlers();
            db = Database.getInstance();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void getAllViews(View view) {
        txtUsername = (EditText) view.findViewById(R.id.txtUsernameRegister);
        txtEmail = (EditText) view.findViewById(R.id.txtEmailRegister);
        txtPassword = (EditText) view.findViewById(R.id.txtPasswordRegister);
        txtConfirmPassword = (EditText) view.findViewById(R.id.txtConfirmPasswordRegister);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        mRoot = (RelativeLayout) view.findViewById(R.id.layoutRegister);
    }

    private void initEventHandlers() {
        btnRegister.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if(v == btnRegister) {
            doRegister();
        }
    }

    //Checking the given account details
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void doRegister() {
        try {
            boolean usernameEntered = true;
            boolean emailEntered = true;
            boolean passwordEntered = true;
            boolean passwordConfirmedEntered = true;

            boolean usernameOK = true;
            boolean emailOK =true;
            boolean passwordOK =true;

            if (txtUsername.getText().length() < 1) {
                usernameEntered = false;
                txtUsername.setError("Enter Username!");
            }

            if (txtEmail.getText().length() < 1) {
                usernameEntered = false;
                txtEmail.setError("Enter Email!");
            }

            if (txtPassword.getText().length() < 1) {
                passwordEntered = false;
                txtPassword.setError("Enter Password!");
            }

            if (txtConfirmPassword.getText().length() < 1) {
                passwordConfirmedEntered = false;
                txtConfirmPassword.setError("You need to confirm your Password!");
            }

            //Check if everything got entered
            if (usernameEntered && emailEntered && passwordEntered && passwordConfirmedEntered) {
                //Check if the passwords are equal
                if (txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                    //Check if they given details are ok
                    if (txtUsername.getText().length() < 4) {
                        usernameOK = false;
                        txtUsername.setError("Username needs to be at least 4 characters long!");
                    }

                    if (!txtEmail.getText().toString().contains("@") || txtEmail.getText().length() < 4 || !txtEmail.getText().toString().contains(".")) {
                        emailOK = false;
                        txtEmail.setError("Email address not valid!");
                    }
                    if (txtPassword.getText().length() < 4) {
                        passwordOK = false;
                        txtPassword.setError("Password needs to be at least 4 characters long!");
                    }

                    if (usernameOK && emailOK && passwordOK) {
                        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.standard_profile_picuture);
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                        byte[] image=stream.toByteArray();

                        User user = new User(txtUsername.getText().toString(), txtPassword.getText().toString(), txtEmail.getText().toString(), image);

                        CreateUserTask createUserTask = new CreateUserTask(getActivity());
                        createUserTask.execute("users", user);

                        txtUsername.setText("");
                        txtPassword.setText("");
                        txtEmail.setText("");
                        txtConfirmPassword.setText("");
                    }
                } else {
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                    Snackbar.make(mRoot, "Your passwords are not equal!", Snackbar.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
