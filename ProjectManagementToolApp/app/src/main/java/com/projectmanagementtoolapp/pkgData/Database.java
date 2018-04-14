package com.projectmanagementtoolapp.pkgData;

import java.util.ArrayList;

/**
 * Created by alexk on 09.10.2017.
 */

public class Database {
    private static Database db;
    public static String url = "http://192.168.101.1:64634/ProjectManagementToolWebservice/webresources/";
    private User currentUser;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Role> roles = new ArrayList<>();

    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public User getUserByName(String username) {
        User retValue = null;

        for (User user : users ) {
            if (user.getUsername().equals(username)) {
                retValue = user;
            }
        }

        return retValue;
    }

    public Role getRoleByName(String name) {
        Role retValue = null;

        for (Role role : roles ) {
            if (role.getName().equals(name)) {
                retValue = role;
            }
        }

        return retValue;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }
}