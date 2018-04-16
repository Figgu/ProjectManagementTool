package com.projectmanagementtoolapp.pkgActivities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgTasks.GetAllUsersTask;
import com.projectmanagementtoolapp.pkgTasks.UpdateUserTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Figgu on 17.10.2017.
 */

public class ShowProfileActivity extends AppCompatActivity implements View.OnClickListener{

    //GUI elements
    private EditText txtName;
    private EditText txtPassword;
    private EditText txtEmail;
    private TextView lblName;
    private TextView lblPassword;
    private TextView lblEmail;
    private ImageView profilePicture;
    private Button btnEditImage;
    private Switch showPassword;
    private static final int RESULT_LOAD_IMAGE = 1;
    private RelativeLayout mRoot;

    //non gui element
    private Database db;
    private MenuItem mSave;
    private MenuItem mEdit;
    private MenuItem mCancel;
    private boolean shownAsPassword = true;
    public static final int PICK_IMAGE = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        getAllViews();
        initEventHandlers();
        db = Database.getInstance();

        fillGUI();
    }

    public void fillGUI() {
        Bitmap bmp = BitmapFactory.decodeByteArray(db.getCurrentUser().getProfilepicture(), 0, db.getCurrentUser().getProfilepicture().length);
        profilePicture.setImageBitmap(bmp);

        lblName.setText(db.getCurrentUser().getUsername());
        lblPassword.setText(db.getCurrentUser().getPassword());
        lblEmail.setText(db.getCurrentUser().getEmail());
        txtName.setText(db.getCurrentUser().getUsername());
        txtPassword.setText(db.getCurrentUser().getPassword());
        txtEmail.setText(db.getCurrentUser().getEmail());
    }

    private void getAllViews() {
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        lblName = (TextView) findViewById(R.id.lblName);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblPassword = (TextView) findViewById(R.id.lblPassword);
        profilePicture = (ImageView) findViewById(R.id.IVPP);
        btnEditImage = (Button) findViewById(R.id.btnEditImage);
        showPassword = (Switch) findViewById(R.id.showPassword);
        mRoot = (RelativeLayout) findViewById(R.id.layoutShowProfile);
    }

    private void initEventHandlers() {
        btnEditImage.setOnClickListener(this);
        showPassword.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_show_profile_edit, menu);
        mSave = menu.findItem(R.id.save_profile);
        mEdit = menu.findItem(R.id.edit_profile);
        mCancel = menu.findItem(R.id.cancel_edit);
        mCancel.setVisible(false);
        mSave.setVisible(false);
        return true;
    }

    public void changeView() {
        mCancel.setVisible(false);
        mSave.setVisible(false);
        mEdit.setVisible(true);
        lblName.setVisibility(View.VISIBLE);
        lblEmail.setVisibility(View.VISIBLE);
        lblPassword.setVisibility(View.VISIBLE);
        txtName.setVisibility(View.INVISIBLE);
        txtPassword.setVisibility(View.INVISIBLE);
        txtEmail.setVisibility(View.INVISIBLE);
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
                case R.id.edit_profile:
                    mCancel.setVisible(true);
                    if (shownAsPassword)
                        txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    else
                        txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);

                    txtPassword.setCursorVisible(true);

                    mSave.setVisible(true);
                    mEdit.setVisible(false);
                    lblName.setVisibility(View.INVISIBLE);
                    lblEmail.setVisibility(View.INVISIBLE);
                    lblPassword.setVisibility(View.INVISIBLE);
                    txtName.setVisibility(View.VISIBLE);
                    txtPassword.setVisibility(View.VISIBLE);
                    txtEmail.setVisibility(View.VISIBLE);
                    return true;
                case R.id.save_profile:
                    boolean usernameOK = true,
                            emailOK = true,
                            passwordOK = true;

                    if (txtName.getText().length() < 4) {
                        usernameOK = false;
                        txtName.setError("Username needs to be at least 4 characters long!");
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
                        if (shownAsPassword)
                            lblPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        else
                            lblPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);

                        User newUser = new User(db.getCurrentUser().getUserid(), txtName.getText().toString(), txtPassword.getText().toString(), txtEmail.getText().toString());
                        System.out.println("--id: " +newUser.getUserid());
                        System.out.println("--name: "+newUser.getUsername());
                        System.out.println("--email: "+newUser.getEmail());

                        UpdateUserTask updateUserTask = new UpdateUserTask(this);
                        updateUserTask.execute("users", newUser);
                    }

                    return true;

                case R.id.cancel_edit:
                    changeView();
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == btnEditImage)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        } else if(v == showPassword) {
            if(shownAsPassword) {
                lblPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                shownAsPassword = false;
            } else {
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                lblPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                shownAsPassword = true;
            }
        }
    }

    public void makeSnackbar(String msg) {
        Snackbar.make(mRoot, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        try {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImage = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                profilePicture.setImageBitmap(yourSelectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 50, stream);
                User user = db.getCurrentUser();
                user.setProfilepicture(stream.toByteArray());
                UpdateUserTask updateUserTask = new UpdateUserTask(this);
                updateUserTask.execute("users", user);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
