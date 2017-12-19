/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alexk
 */
@Entity
public class Project implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String projectID;
    
    private String name;
    private String description;
    private Date projectBegin;
    
    @OneToMany(cascade = CascadeType.ALL,
        mappedBy = "project", orphanRemoval = true)
    private ArrayList<User> contributors;
    
    @OneToMany(cascade = CascadeType.ALL,
        mappedBy = "project", orphanRemoval = true)
    private ArrayList<Sprint> sprints;

    public Project(String projectID, String name, String description, ArrayList<User> contributors, ArrayList<Sprint> sprints) {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.contributors = contributors;
        this.sprints = sprints;
        
        contributors = new ArrayList<>();
        sprints = new ArrayList<>();
    }
    
    private Project() {
        contributors = new ArrayList<>();
        sprints = new ArrayList<>();
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
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

    public ArrayList<User> getContributors() {
        return contributors;
    }

    public void setContributors(ArrayList<User> contributors) {
        this.contributors = contributors;
    }

    public ArrayList<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(ArrayList<Sprint> sprints) {
        this.sprints = sprints;
    }

    public Date getProjectBegin() {
        return projectBegin;
    }

    public void setProjectBegin(Date projectBegin) {
        this.projectBegin = projectBegin;
    }
    
    
}
