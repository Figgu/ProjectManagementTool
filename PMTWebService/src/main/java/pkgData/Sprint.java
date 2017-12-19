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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;


/**
 *
 * @author alexk
 */
@Entity
public class Sprint implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String sprintID;
    
    private Date startDate;
    private Date endDate;
    
    @ManyToOne
    private Project project;
    
    @OneToMany(cascade = CascadeType.ALL,
        mappedBy = "project", orphanRemoval = true)
    private ArrayList<Issue> issues;

    public Sprint(String sprintID, Date startDate, Date endDate, Project project, ArrayList<Issue> issues) {
        this.sprintID = sprintID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
        this.issues = issues;
    }

    public String getSprintID() {
        return sprintID;
    }

    public void setSprintID(String sprintID) {
        this.sprintID = sprintID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ArrayList<Issue> getIssues() {
        return issues;
    }

    public void setIssues(ArrayList<Issue> issues) {
        this.issues = issues;
    }
    
    
    
}
