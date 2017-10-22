package com.projectmanagementtoolapp.pkgData;

import java.util.Date;
import java.util.List;

/**
 * Created by alexk on 11.10.2017.
 */

public class Project {
    private int projectID;
    private String name;
    private String description;
    private List<User> contributors;
    private Date startDate;
    private List<Sprint> sprints;

    public Project(String name, String description, List<User> contributors, Date startDate) {
        this.name = name;
        this.description = description;
        this.contributors = contributors;
        this.startDate = startDate;
    }

    public Project(String name, String description, Date startDate) {
        this.name = name;
        this.description = description;
        this.contributors = contributors;
        this.startDate = startDate;
    }

    public Project() {

    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getContributors() {
        return contributors;
    }

    public void setContributors(List<User> contributors) {
        this.contributors = contributors;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    @Override
    public String toString() {
        return getName();
    }
}
