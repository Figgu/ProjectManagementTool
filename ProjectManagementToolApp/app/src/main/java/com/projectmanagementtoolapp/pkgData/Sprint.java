package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexk on 11.10.2017.
 */

public class Sprint implements Serializable{
    private int sprintid;
    private Project project;
    private Date startdate;
    private Date enddate;
    private ArrayList<Issue> issues;

    public Sprint(Project project, Date startdate, Date enddate) {
        this.project = project;
        this.startdate = startdate;
        this.enddate = enddate;

        this.issues = new ArrayList<>();
    }

    public Sprint() {
        this.issues = new ArrayList<>();
    }

    public int getSprintID() {
        return sprintid;
    }

    public void setSprintID(int sprintID) {
        this.sprintid = sprintID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd. MMM. yyyy");
        return "Sprint from " + dateFormat.format(startdate) + " to " + dateFormat.format(enddate);
    }
}