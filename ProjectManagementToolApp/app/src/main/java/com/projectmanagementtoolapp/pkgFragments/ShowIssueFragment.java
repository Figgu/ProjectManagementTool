package com.projectmanagementtoolapp.pkgFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.projectmanagementtoolapp.R;
import com.projectmanagementtoolapp.pkgData.Issue;
import com.projectmanagementtoolapp.pkgData.IssueStatus;
import com.projectmanagementtoolapp.pkgData.User;
import com.projectmanagementtoolapp.pkgTasks.UpdateIssueTask;

import java.util.ArrayList;

import okhttp3.internal.connection.RealConnection;


public class ShowIssueFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private RelativeLayout mRoot;
    private TextView textView;
    private Spinner spStatus;
    private Button btnSave;
    private Button btnCancel;
    private ListView listIssues;

    private Issue currentIssue;

    public ShowIssueFragment() {
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
        View view = null;
        try {
            getActivity().setTitle("Show Issue");
            currentIssue = (Issue) this.getArguments().getSerializable("issue");
            view = inflater.inflate(R.layout.fragment_show_issue, container, false);
            getAllViews(view);
            initEventhandlers();
            initSpinner();
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        return view;
    }

    private void initSpinner() {
        ArrayList<String> status = new ArrayList<>();
        status.add(IssueStatus.TODO.toString());
        status.add(IssueStatus.INPROCESS.toString());
        status.add(IssueStatus.INREVIEW.toString());
        status.add(IssueStatus.DONE.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, status);
        spStatus.setAdapter(adapter);
    }

    private void getAllViews(View view) {
        mRoot = (RelativeLayout) view.findViewById(R.id.showIssueRel);
        textView = (TextView) view.findViewById(R.id.issueDescription);
        spStatus = (Spinner) view.findViewById(R.id.issueStatus);
        btnSave = (Button) view.findViewById(R.id.btnSaveIssue);
        btnCancel = (Button) view.findViewById(R.id.btnCancelIssue);
        textView.setText(currentIssue.getDescription());
        listIssues = (ListView) view.findViewById(R.id.issueList);
    }

    private void initEventhandlers() {
        spStatus.setOnItemSelectedListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String status = (String) spStatus.getSelectedItem();
        currentIssue.setStatus(status);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v==btnSave) {
            UpdateIssueTask updateIssueTask = new UpdateIssueTask(getActivity());
            updateIssueTask.execute("issues", currentIssue);
        } else {
            getActivity().finish();
            getActivity().startActivity(getActivity().getIntent());
        }
    }
}