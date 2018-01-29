package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alexk on 11.10.2017.
 */

public class Project implements Serializable{
    private int projectid;
    private String name;
    private String description;
    private List<Userisinprojectwithrole> users;
    private Date projectbeginn;
    private List<Sprint> sprints;

    public Project(String name, String description, List<Userisinprojectwithrole> users, Date projectbeginn) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.projectbeginn = projectbeginn;

        sprints = new ArrayList<>();
    }

    public Project(int projectid, String name, String description, Date projectbeginn) {
        this.projectid = projectid;
        this.name = name;
        this.description = description;
        this.projectbeginn = projectbeginn;

        sprints = new ArrayList<>();
    }

    public Project(String name, String description, Date projectbeginn) {
        this.name = name;
        this.description = description;
        this.projectbeginn = projectbeginn;

        sprints = new ArrayList<>();
    }

    public Project(int projectid, String name, String description, List<Userisinprojectwithrole> users, Date projectbeginn) {
        this.projectid = projectid;
        this.name = name;
        this.description = description;
        this.users = users;
        this.projectbeginn = projectbeginn;

        sprints = new ArrayList<>();
    }

    public Project() {
        sprints = new ArrayList<>();
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
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

    public List<Userisinprojectwithrole> getUsers() {
        return users;
    }

    public void setUsers(List<Userisinprojectwithrole> users) {
        this.users = users;
    }

    public Date getProjectbeginn() {
        return projectbeginn;
    }

    public void setProjectbeginn(Date projectbeginn) {
        this.projectbeginn = projectbeginn;
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
