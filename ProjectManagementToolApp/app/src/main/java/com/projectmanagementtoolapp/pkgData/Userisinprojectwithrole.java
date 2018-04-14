package com.projectmanagementtoolapp.pkgData;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

/**
 * Created by alexk on 26.01.2018.
 */

public class Userisinprojectwithrole implements Serializable {
    private UserisinprojectwithrolePK userisinprojectwithrolePK;
    private Project project;
    private Role roleid;
    private User user;

    public Userisinprojectwithrole(Project project, Role roleid, User user) {
        this.project = project;
        this.roleid = roleid;
        this.user = user;
    }

    public Userisinprojectwithrole(Project project, User user) {
        this.project = project;
        this.user = user;
    }

    public Userisinprojectwithrole() {

    }

    public UserisinprojectwithrolePK getUserisinprojectwithrolePK() {
        return userisinprojectwithrolePK;
    }

    public void setUserisinprojectwithrolePK(UserisinprojectwithrolePK userisinprojectwithrolePK) {
        this.userisinprojectwithrolePK = userisinprojectwithrolePK;
    }

    public Project getProject() {
        return project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    public Role getRoleid() {
        return roleid;
    }

    public void setRoleid(Role roleid) {
        this.roleid = roleid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        if (roleid != null)
            return "User: " + user.getUsername() + "\n" + "Role: " + roleid.getName();
        else
            return "User: " + user.getUsername() + "\n" + "Role: No role added yet";
    }
}