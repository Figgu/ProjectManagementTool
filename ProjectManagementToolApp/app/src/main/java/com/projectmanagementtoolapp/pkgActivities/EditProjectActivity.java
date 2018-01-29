package com.projectmanagementtoolapp.pkgActivities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Database;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgData.Userisinprojectwithrole;
import com.projectmanagementtoolapp.pkgTasks.GetUsersOfProjectTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class EditProjectActivity extends AppCompatActivity implements View.OnClickListener {


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
    private Project currentProject;
    private ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        contributors = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);      //Back navigation
        currentProject = (Project) getIntent().getSerializableExtra("project");
        setTitle("Update Project: " + currentProject);
        getAllViews();
        initEventhandlers();
        db = Database.getInstance();

        GetUsersOfProjectTask getUsersOfProjectTask = new GetUsersOfProjectTask(this);
        getUsersOfProjectTask.execute("projects/users", currentProject.getProjectid());
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
            case R.id.delete_project:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Delete");
                builder.setMessage("Do you really want to delete this project?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteProject();
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

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //TODO DELETE SPRINTS/ISSUES
    private void deleteProject() {
        //DeleteUsersFromProject deleteUsersFromProject = new DeleteUsersFromProject(this);
        //deleteUsersFromProject.execute(currentProject.getProjectid());
        //try {
           // String result = deleteUsersFromProject.get();
       // } catch (InterruptedException e) {
       //     e.printStackTrace();
       // } catch (ExecutionException e) {
       //     e.printStackTrace();
       // }


        //DeleteProjectTask deleteProjectTask = new DeleteProjectTask(this);
        //deleteProjectTask.execute(currentProject);
        //try {
        //    String result = deleteProjectTask.get();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
       // } catch (ExecutionException e) {
       //     e.printStackTrace();
        //}
        this.finish();
    }

    // Listener
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay);

            if (!isDateOK(calendar.getTime())) {
                Snackbar.make(mRoot, "Project start cant be in the past!", Snackbar.LENGTH_LONG).show();
            } else {
                txtProjectStart.setText(dateFormat.format(calendar.getTime()));
            }
        }
    };

    private void getAllViews() {
        txtProjectName = (EditText) findViewById(R.id.txtProjectNameEdit);
        txtProjectDescription = (EditText) findViewById(R.id.txtProjectDescriptionEdit);
        txtContributorName = (EditText) findViewById(R.id.txtContributorNameEdit);
        imgAddButton = (ImageView) findViewById(R.id.imgAddButtonEdit);
        txtProjectStart = (EditText) findViewById(R.id.txtProjectStartEdit);
        btnAddProject = (Button) findViewById(R.id.btnAddProjectEdit);
        mRoot = (RelativeLayout) findViewById(R.id.linearLayoutEditProject);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutContributorsEdit);
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
                User user = db.getUserByName(txtContributorName.getText().toString());
                if (db.getUsers().contains(user)) {
                    if (!contributors.contains(user)) {
                        contributors.add(db.getUserByName(txtContributorName.getText().toString()));
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

                                if(!user.equals(db.getCurrentUser()))
                                    contributors.remove(user);
                                else
                                    Snackbar.make(mRoot, "You cant remove yourself from the project!", Snackbar.LENGTH_LONG).show();
                            }
                        });

                        linearLayout.addView(view);
                        txtContributorName.setText("");
                    } else {
                        Snackbar.make(mRoot, "User already in project!", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(mRoot, "User not found!", Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(mRoot, "Please add an contributor!", Snackbar.LENGTH_LONG).show();
            }
        } else if (v == btnAddProject) {
            try {
                updateProject();
            } catch (ParseException e) {
                Snackbar.make(mRoot, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            } catch (InterruptedException e) {
                Snackbar.make(mRoot, "Error: " + e.getMessage(), Snackbar.LENGTH_LONG).show();
            } catch (ExecutionException e) {
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

    public void fillUsers(ArrayList<Userisinprojectwithrole> userWithRoles) {
        for (Userisinprojectwithrole user : userWithRoles) {
            users.add(user.getUser());
        }
    }

    public void fillGUI() {
        txtProjectName.setText(currentProject.getName());
        txtProjectDescription.setText(currentProject.getDescription());
        txtProjectStart.setText(dateFormat.format(currentProject.getProjectbeginn()));

        adapter = new ArrayAdapter<>(this, R.layout.list_view_add_contributors, R.id.contributorNameAdd, users);

        for (int i=0; i<users.size(); i++) {
            final View view = adapter.getView(i, null, null);
            final View child = view.findViewById(R.id.imgDeleteCon);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) view.findViewById(R.id.contributorNameAdd);
                    User user = db.getUserByName(textView.getText().toString());

                    if(!user.getUsername().equals(db.getCurrentUser().getUsername())) {
                        linearLayout.removeView(view);
                        deleteFromListByName(user.getUsername());
                        System.out.println(contributors.contains(user));
                        System.out.println("IN ONCLICK: " + contributors.get(0).getUserid());
                    } else {
                        Snackbar.make(mRoot, "You cant remove yourself from the project!", Snackbar.LENGTH_LONG).show();
                    }
                }
            });

            contributors.add(users.get(i));
            linearLayout.addView(view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_edit_project, menu);
        return true;
    }

    private void deleteFromListByName(String name) {
        Iterator<User> it = contributors.iterator();
        User toDelete = null;

        while (it.hasNext()) {
            User currentUser = it.next();
            if (currentUser.getUsername().equals(name)) {
                toDelete = currentUser;
            }
        }

        contributors.remove(toDelete);
    }

    private boolean isDateOK(Date date) {
        boolean retVal = true;
        Date currentTime = Calendar.getInstance().getTime();

        if (date.before(currentTime))
            retVal = false;

        return retVal;
    }

    private void updateProject() throws ParseException, InterruptedException, ExecutionException {
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
            //Project project = new Project(currentProject.getProjectid(), txtProjectName.getText().toString(), txtProjectDescription.getText().toString(), contributors, date);

            /*
            DeleteUsersFromProject deleteUsersFromProject = new DeleteUsersFromProject(this);
            deleteUsersFromProject.execute(currentProject.getProjectid());
            String result = deleteUsersFromProject.get();

            UpdateProjectTask updateProjectTask = new UpdateProjectTask(this);
            //updateProjectTask.execute(project);
            result = updateProjectTask.get();

            System.out.println("HERE: " + contributors);
            //Add the users to the project
            InsertUsersInProject insertUsersInProject = new InsertUsersInProject(this);
            insertUsersInProject.execute(contributors, currentProject.getProjectid());
            result = insertUsersInProject.get();
*/
            this.finish();
        }
    }
}
