package com.projectmanagementtoolapp.pkgFragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Project;
import com.projectmanagementtoolapp.pkgData.Sprint;
import com.projectmanagementtoolapp.pkgTasks.CreateSprintTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

public class AddSprintFragment extends Fragment implements View.OnClickListener {
    private EditText txtStartDateSprint;
    private EditText txtEndDateSprint;
    private Button btnAddSprint;
    private Button btnCancel;
    private Activity mActivity;
    private FrameLayout layoutFragment;

    //Non gui attributes
    private Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Project currentProject;
    private View lastView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_sprint, container, false);

        currentProject = (Project) this.getArguments().getSerializable("currentProject");
        getAllViews(view);
        addEventListeners();

        return view;
    }

    // Listener
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(selectedYear, selectedMonth, selectedDay);
            Date currentTime = Calendar.getInstance().getTime();

            if (lastView == txtStartDateSprint) {
                try {
                    if (txtEndDateSprint.getText().length() > 1) {
                        if (calendar.getTime().before(dateFormat.parse(txtEndDateSprint.getText().toString()))) {
                            if (!dateFormat.format(calendar.getTime()).equals(txtStartDateSprint.getText().toString())) {
                                txtStartDateSprint.setText(dateFormat.format(calendar.getTime()));
                            } else {
                                Snackbar.make(layoutFragment, "Start date and end date cant be on the same day!", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(layoutFragment, "Start date cant be after end date", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        txtStartDateSprint.setText(dateFormat.format(calendar.getTime()));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (txtStartDateSprint.getText().length() > 1) {
                        if (calendar.getTime().after(dateFormat.parse(txtStartDateSprint.getText().toString()))) {
                            if (!dateFormat.format(calendar.getTime()).equals(dateFormat.parse(txtStartDateSprint.getText().toString()).getTime())) {
                                txtEndDateSprint.setText(dateFormat.format(calendar.getTime()));
                            } else {
                                Snackbar.make(layoutFragment, "Start date and end date cant be on the same day!", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(layoutFragment, "End date cant be before start date", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        txtEndDateSprint.setText(dateFormat.format(calendar.getTime()));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    public AddSprintFragment() {
    }

    private void getAllViews(View v) {
        txtStartDateSprint = (EditText) v.findViewById(R.id.txtStartDateAddSprint);
        txtEndDateSprint = (EditText)v.findViewById(R.id.txtEndDateAddSprint);
        btnAddSprint = (Button) v.findViewById(R.id.btnAddSprint);
        btnCancel = (Button) v.findViewById(R.id.btnCancelSprint);
        layoutFragment = (FrameLayout) v.findViewById(R.id.fragmentAddSprint);
    }

    private void addEventListeners() {
        txtStartDateSprint.setOnClickListener(this);
        txtEndDateSprint.setOnClickListener(this);
        btnAddSprint.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == txtStartDateSprint) {
            lastView = txtStartDateSprint;
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    datePickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setTitle("Select start date of sprint");
            datePickerDialog.show();

        } else if (v == txtEndDateSprint) {
            lastView = txtEndDateSprint;
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    datePickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setTitle("Select end date of sprint");
            datePickerDialog.show();

        } else if (v == btnAddSprint) {
            createSprint();
        } else if (v == btnCancel){
            mActivity.finish();
            mActivity.startActivity(mActivity.getIntent());
        }
    }

    public void createSprint() {
        try {
            boolean startDateEntered = true;
            boolean endDateEntered = true;

            if (txtStartDateSprint.getText().length() < 0) {
                txtStartDateSprint.setError("Enter start date!");
                startDateEntered = false;
            }

            if (txtEndDateSprint.getText().length() < 0) {
                txtEndDateSprint.setError("Enter end date!");
                endDateEntered = false;
            }

            if (startDateEntered && endDateEntered) {
                Date startDate = dateFormat.parse(txtStartDateSprint.getText().toString());
                Date endDate = dateFormat.parse(txtEndDateSprint.getText().toString());

                Sprint createdSprint = new Sprint(currentProject, startDate, endDate);

                if (checkInput(createdSprint)) {
                    CreateSprintTask createSprintTask = new CreateSprintTask(getActivity());
                    createSprintTask.execute("sprints", createdSprint);
                } else {
                    Toast.makeText(getActivity(), "A new sprint cant start in an old sprint", Toast.LENGTH_LONG).show();
                }
            }
        } catch (ParseException e) {
            Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkInput(Sprint sprint) {
        boolean retValue = true;

        for (Sprint sp : currentProject.getSprints()) {
            if (sprint.getStartdate().before(sp.getEnddate())) {
                retValue = false;
            }
        }

        return retValue;
    }
}