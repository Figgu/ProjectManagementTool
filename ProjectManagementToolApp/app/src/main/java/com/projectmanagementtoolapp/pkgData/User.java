package com.projectmanagementtoolapp.pkgData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by alexk on 09.10.2017.
 */

public class User implements Serializable{
    private int userid;
    private String username;
    private String password;
    private String email;
    private byte[] profilepicture;
    private List<Project> projects;
    private HashMap<Project, ArrayList<Role>> rolesInProject;

    public User(int userid, String username, String password, String email) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;

        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public User(int userid, String username, String password, String email, byte[] profilepicture) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.profilepicture = profilepicture;

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

    public User(String username, String password, String email, byte[] profilePicture) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profilepicture = profilePicture;

        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public User(String username) {
        this.username = username;

        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public User() {
        projects = new ArrayList<>();
        rolesInProject =  new HashMap<>();
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
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

    public byte[] getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(byte[] profilePicture) {
        this.profilepicture = profilePicture;
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
