package com.projectmanagementtoolapp.pkgActivities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgTasks.ChangeProfilePictureTask;
import com.projectmanagementtoolapp.pkgTasks.ChangeUserTask;
import com.projectmanagementtoolapp.pkgTasks.InsertUserTask;
import com.projectmanagementtoolapp.pkgTasks.SelectAllUsersTask;

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
    private Button btnEdit;
    private Button btnSave;
    private ImageButton btnEditImage;
    private static final int RESULT_LOAD_IMAGE = 1;

    //non gui elements
    private Database db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showprofile);

        setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation

        getAllViews();
        initEventHandlers();
        db = Database.getInstance();
        User currentUser = db.getCurrentUser();
        lblName.setText(currentUser.getUsername());
        lblPassword.setText(currentUser.getPassword());
        lblEmail.setText(currentUser.getEmail());
        txtName.setText(currentUser.getUsername());
        txtPassword.setText(currentUser.getPassword());
        txtEmail.setText(currentUser.getEmail());
    }

    private void getAllViews() {
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        lblName = (TextView) findViewById(R.id.lblName);
        lblEmail = (TextView) findViewById(R.id.lblEmail);
        lblPassword = (TextView) findViewById(R.id.lblPassword);
        profilePicture = (ImageView) findViewById(R.id.IVPP);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnEditImage = (ImageButton) findViewById(R.id.btnEditImage);
    }

    private void initEventHandlers() {

        btnEdit.setOnClickListener(this);
        btnEditImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    /*
    For back navigation to parent activity
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btnEdit) {
            lblName.setVisibility(View.INVISIBLE);
            lblEmail.setVisibility(View.INVISIBLE);
            lblPassword.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
            txtName.setVisibility(View.VISIBLE);
            txtPassword.setVisibility(View.VISIBLE);
            txtEmail.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
        }
        if(v == btnSave) {
            lblName.setVisibility(View.VISIBLE);
            lblEmail.setVisibility(View.VISIBLE);
            lblPassword.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
            txtName.setVisibility(View.INVISIBLE);
            txtPassword.setVisibility(View.INVISIBLE);
            txtEmail.setVisibility(View.INVISIBLE);
            btnSave.setVisibility(View.INVISIBLE);

            ChangeUserTask changeUserTask = new ChangeUserTask(this);
            User currentUser = db.getCurrentUser();
            changeUserTask.execute(txtName.getText().toString(), txtPassword.getText().toString(), txtEmail.getText().toString(), Integer.toString(currentUser.getUserID()));
            db = Database.getInstance();
            db.setCurrentUser(new User(txtName.getText().toString(), txtPassword.getText().toString(), txtEmail.getText().toString()));
            currentUser = db.getCurrentUser();
            lblName.setText(currentUser.getUsername());
            lblPassword.setText(currentUser.getPassword());
            lblEmail.setText(currentUser.getEmail());
            txtName.setText(currentUser.getUsername());
            txtPassword.setText(currentUser.getPassword());
            txtEmail.setText(currentUser.getEmail());
            db = Database.getInstance();

        }
        if(v == btnEditImage)
        {
            Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.IVPP);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            ChangeProfilePictureTask cppt = new ChangeProfilePictureTask(this);
            cppt.execute(db.getCurrentUser().getUserID(), picturePath);
        }
    }
}
