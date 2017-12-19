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
    
    
}
