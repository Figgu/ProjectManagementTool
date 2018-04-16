package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;

/**
 * Created by alexk on 16.04.2018.
 */

public class UserisinissuePK implements Serializable{
    private int userid;
    private int issueid;

    public UserisinissuePK() {

    }

    public UserisinissuePK(int userid, int issueid) {
        this.userid = userid;
        this.issueid = issueid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getIssueid() {
        return issueid;
    }

    public void setIssueid(int issueid) {
        this.issueid = issueid;
    }
}
