package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;

/**
 * Created by alexk on 16.04.2018.
 */

public class Userisinissue implements Serializable {
    private UserisinissuePK userisinissuePK;
    private Issue issue;
    private User user;

    public Userisinissue(Issue issue, User user) {
        this.issue = issue;
        this.user = user;
    }

    public Userisinissue(){

    }

    public UserisinissuePK getUserisinissuePK() {
        return userisinissuePK;
    }

    public void setUserisinissuePK(UserisinissuePK userisinissuePK) {
        this.userisinissuePK = userisinissuePK;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
