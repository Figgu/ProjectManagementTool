package com.projectmanagementtoolapp.pkgData;

/**
 * Created by alexk on 11.10.2017.
 */

public class Issue {
    private int IssueID;
    private Sprint sprint;
    private String name;
    private String description;
    private IssueStatus status;

    public Issue(Sprint sprint, String name, String description, IssueStatus status) {
        this.sprint = sprint;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Issue() {

    }

    public int getIssueID() {
        return IssueID;
    }

    public void setIssueID(int issueID) {
        IssueID = issueID;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
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

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name;
    }
}
