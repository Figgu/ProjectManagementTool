package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;

/**
 * Created by alexk on 29.01.2018.
 */

public class UserisinprojectwithrolePK implements Serializable{
    private int userid;
    private int projectid;

    public UserisinprojectwithrolePK(int userid, int projectid) {
        this.userid = userid;
        this.projectid = projectid;
    }

    public UserisinprojectwithrolePK() {

    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
}
