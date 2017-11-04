package com.projectmanagementtoolapp.pkgData;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by alexk on 11.10.2017.
 */

public class Sprint implements Serializable{
    private int sprintID;
    private Project project;
    private Date startDate;
    private Date endDate;
    private ArrayList<Issue> issues;

    public Sprint(Project project, Date startDate, Date endDate) {
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;

        this.issues = new ArrayList<>();
    }

    public Sprint() {
        this.issues = new ArrayList<>();
    }

    public int getSprintID() {
        return sprintID;
    }

    public void setSprintID(int sprintID) {
        this.sprintID = sprintID;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "Sprint " + startDate + " - " + endDate;
    }
}