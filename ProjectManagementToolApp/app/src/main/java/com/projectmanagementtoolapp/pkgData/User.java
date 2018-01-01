package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alexk on 09.10.2017.
 */

public class User implements Serializable{
    private int userID;
    private String username;
    private String password;
    private String email;
    private byte[] profilePicture;
    private List<Project> projects;
    private HashMap<Project, ArrayList<Role>> rolesInProject;

    public User(int userID, String username, String password, String email) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.email = email;

        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;

        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public User() {
        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public HashMap<Project, ArrayList<Role>> getRolesInProject() {
        return rolesInProject;
    }

    public void setRolesInProject(HashMap<Project, ArrayList<Role>> rolesInProject) {
        this.rolesInProject = rolesInProject;
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
