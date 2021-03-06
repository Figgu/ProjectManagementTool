package com.projectmanagementtoolapp.pkgActivities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgData.Userisinprojectwithrole;
import com.projectmanagementtoolapp.pkgTasks.AddUserToProjectTask;
import com.projectmanagementtoolapp.pkgTasks.CreateProjectTask;
import com.projectmanagementtoolapp.pkgTasks.GetAllRolesTask;
import com.projectmanagementtoolapp.pkgTasks.GetAllUsersTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import okhttp3.Response;

public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {

    //GUI elements
    private EditText txtProjectName;
    private EditText txtProjectDescription;
    private EditText txtContributorName;
    private ImageView imgAddButton;
    private EditText txtProjectStart;
    private Button btnAddProject;
    private RelativeLayout mRoot;
    private LinearLayout linearLayout;

    //Other elements
    private ArrayList<User> contributors;
    private Database db;
    private Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MMM. yyyy");
    private ArrayAdapter<User> adapter;

    // Listener
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay);
            txtProjectStart.setText(dateFormat.format(calendar.getTime()));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        contributors = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation
        setTitle("Add new project");
        getAllViews();
        initEventhandlers();
        db = Database.getInstance();

        GetAllUsersTask getAllUsersTask = new GetAllUsersTask(this);
        getAllUsersTask.execute("users");
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

    private void getAllViews() {
        txtProjectName = (EditText) findViewById(R.id.txtProjectName);
        txtProjectDescription = (EditText) findViewById(R.id.txtProjectDescription);
        txtContributorName = (EditText) findViewById(R.id.txtContributorName);
        imgAddButton = (ImageView) findViewById(R.id.imgAddButton);
        txtProjectStart = (EditText) findViewById(R.id.txtProjectStart);
        btnAddProject = (Button) findViewById(R.id.btnAddProject);
        mRoot = (RelativeLayout) findViewById(R.id.layoutAddProject);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutContributors);
    }

    private void initEventhandlers() {
        imgAddButton.setOnClickListener(this);
        btnAddProject.setOnClickListener(this);
        txtProjectStart.setOnClickListener(this);
        linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == imgAddButton) {
            if (txtContributorName.getText().length() > 1) {
                String enteredUsername = txtContributorName.getText().toString();
                User user = db.getUserByName(enteredUsername);

                if (db.getUsers().contains(user)) {
                    if (!contributors.contains(user)) {
                        if(!user.getUsername().equals(db.getCurrentUser().getUsername())) {
                            contributors.add(user);
                            System.out.println(contributors);
                            adapter = new ArrayAdapter<>(this, R.layout.list_view_add_contributors, R.id.contributorNameAdd, contributors);
                            final View view = adapter.getView(adapter.getCount()-1, null, null);
                            final View child = view.findViewById(R.id.imgDeleteCon);
                            child.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    linearLayout.removeView(view);
                                    TextView textView = (TextView) view.findViewById(R.id.contributorNameAdd);
                                    User user = db.getUserByName(textView.getText().toString());
                                    contributors.remove(user);
                                }
                            });

                            linearLayout.addView(view);
                            txtContributorName.setText("");
                        } else {
                            Snackbar.make(mRoot, "You cant add yourself into the project!", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(mRoot, "User already in project!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mRoot, "User not found!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(mRoot, "Input not valid!", Snackbar.LENGTH_LONG).show();
            }
        } else if (v == btnAddProject) {
            try {
                checkInput();
            } catch (ParseException e) {
                Snackbar.make(mRoot, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                Snackbar.make(mRoot, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            } catch (ExecutionException e) {
                Snackbar.make(mRoot, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            } catch (IOException e) {
                Snackbar.make(mRoot, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        } else if (v == txtProjectStart) {
            new DatePickerDialog(this,
                    datePickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show();
        }

    }

    private void checkInput() throws ParseException, InterruptedException, ExecutionException, IOException {
        boolean everythingOK = true;

        if (txtProjectName.getText().length() < 3) {
            txtProjectName.setError("Project name too short!");
            everythingOK = false;
        }

        if (txtProjectDescription.getText().length() < 5) {
            txtProjectDescription.setError("Project description too short!");
            everythingOK = false;
        }

        if (txtProjectStart.getText().length() < 1) {
            txtProjectStart.setError("You need to define a start date!");
            everythingOK = false;
        }

        if (contributors.size() < 1) {
            Snackbar.make(mRoot, "You need to add at least one contributor!", Snackbar.LENGTH_LONG).show();
            everythingOK = false;
        }

        if (everythingOK) {
            Date date = dateFormat.parse(txtProjectStart.getText().toString());
            Project project = new Project(txtProjectName.getText().toString(), txtProjectDescription.getText().toString(), date);

            GetAllRolesTask getAllRolesTask = new GetAllRolesTask(this);
            getAllRolesTask.execute("roles");
            String result = getAllRolesTask.get();

            CreateProjectTask createProjectTask = new CreateProjectTask(this);
            createProjectTask.execute("projects", project);
            String retVal = createProjectTask.get();
        }
    }

    public void createProject(String projectStr) {
        List<Userisinprojectwithrole> userisinprojectwithroleList = new ArrayList<>();
        Project projectWithId = new Gson().fromJson(projectStr, Project.class);

        for (User user : contributors) {
            userisinprojectwithroleList.add(new Userisinprojectwithrole(projectWithId, user));
        }

        userisinprojectwithroleList.add(new Userisinprojectwithrole(projectWithId, db.getRoleByName("ProjectManager"), db.getCurrentUser()));
        AddUserToProjectTask addUserToProjectTask = new AddUserToProjectTask(this);
        addUserToProjectTask.execute("upr", userisinprojectwithroleList);
    }

    public void makeSnackbar(String msg) {
        Snackbar.make(mRoot, msg, Snackbar.LENGTH_LONG).show();
    }
}