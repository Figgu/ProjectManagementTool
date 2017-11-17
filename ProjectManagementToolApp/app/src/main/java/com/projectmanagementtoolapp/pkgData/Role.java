package com.projectmanagementtoolapp.pkgData;

/**
 * Created by alexk on 13.10.2017.
 */

public class Role {
    private int roleID;
    private String name;
    private String description;
    private boolean isUnique;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role()
    {

    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean unique) {
        isUnique = unique;
    }

    @Override
    public String toString() {
        return "ID: " + getRoleID() + " Name: " + getName() + " Description: " + getDescription();
    }
}
