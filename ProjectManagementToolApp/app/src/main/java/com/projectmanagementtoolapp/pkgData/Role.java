package com.projectmanagementtoolapp.pkgData;

/**
 * Created by alexk on 13.10.2017.
 */

public class Role {
    private int roleid;
    private String name;
    private String description;
    private String isunique;

    public Role()
    {

    }

    public Role(String name, String description, String isunique) {
        this.name = name;
        this.description = description;
        this.isunique = isunique;
    }

    public Role(int roleid, String name, String description, String isunique) {
        this.roleid = roleid;
        this.name = name;
        this.description = description;
        this.isunique = isunique;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
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

    public String isUnique() {
        return isunique;
    }

    public void setUnique(String unique) {
        isunique = unique;
    }

    @Override
    public String toString() {
        if (isunique.equals("true"))
            return getName() + "\n" + getDescription() + "\n" + "Unique role in project!";
        else
            return getName() + "\n" + getDescription();
    }
}
