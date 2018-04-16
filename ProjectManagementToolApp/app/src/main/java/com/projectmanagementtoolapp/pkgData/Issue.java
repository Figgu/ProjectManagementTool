package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by alexk on 11.10.2017.
 */

public class Issue implements Serializable{
    private int issueid;
    private String name;
    private String description;
    private ArrayList<Userisinissue> userCollection;
    private String status;
    private Sprint sprint;

    public Issue() {

    }

    public Issue(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Issue(String name, String description, String status, Sprint sprint) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.sprint = sprint;
    }

    public int getIssueID() {
        return issueid;
    }

    public void setIssueID(int issueID) {
        this.issueid = issueID;
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

    public ArrayList<Userisinissue> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(ArrayList<Userisinissue> userCollection) {
        this.userCollection = userCollection;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    @Override
    public String toString() {
        return status + ": " + name;
    }
}
