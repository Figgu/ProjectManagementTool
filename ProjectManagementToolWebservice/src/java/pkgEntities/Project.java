/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexk
 */
@Entity
@Table(name = "PROJECT03", catalog = "", schema = "D5B03")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project03.findAll", query = "SELECT p FROM Project03 p")
    , @NamedQuery(name = "Project03.findByProjectid", query = "SELECT p FROM Project03 p WHERE p.projectid = :projectid")
    , @NamedQuery(name = "Project03.findByName", query = "SELECT p FROM Project03 p WHERE p.name = :name")
    , @NamedQuery(name = "Project03.findByDescription", query = "SELECT p FROM Project03 p WHERE p.description = :description")
    , @NamedQuery(name = "Project03.findByProjectbeginn", query = "SELECT p FROM Project03 p WHERE p.projectbeginn = :projectbeginn")})
public class Project03 implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PROJECTID")
    private BigDecimal projectid;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PROJECTBEGINN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date projectbeginn;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project03")
    private Collection<Userisinprojectwithrole03> userisinprojectwithrole03Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project03")
    private Collection<Sprint03> sprint03Collection;

    public Project03() {
    }

    public Project03(BigDecimal projectid) {
        this.projectid = projectid;
    }

    public BigDecimal getProjectid() {
        return projectid;
    }

    public void setProjectid(BigDecimal projectid) {
        this.projectid = projectid;
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

    public Date getProjectbeginn() {
        return projectbeginn;
    }

    public void setProjectbeginn(Date projectbeginn) {
        this.projectbeginn = projectbeginn;
    }

    @XmlTransient
    public Collection<Userisinprojectwithrole03> getUserisinprojectwithrole03Collection() {
        return userisinprojectwithrole03Collection;
    }

    public void setUserisinprojectwithrole03Collection(Collection<Userisinprojectwithrole03> userisinprojectwithrole03Collection) {
        this.userisinprojectwithrole03Collection = userisinprojectwithrole03Collection;
    }

    @XmlTransient
    public Collection<Sprint03> getSprint03Collection() {
        return sprint03Collection;
    }

    public void setSprint03Collection(Collection<Sprint03> sprint03Collection) {
        this.sprint03Collection = sprint03Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project03)) {
            return false;
        }
        Project03 other = (Project03) object;
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Project03[ projectid=" + projectid + " ]";
    }
    
}
