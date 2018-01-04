/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgEntities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SPRINT03", catalog = "", schema = "D5B03")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sprint.findAll", query = "SELECT s FROM Sprint s")
    , @NamedQuery(name = "Sprint.findBySprintid", query = "SELECT s FROM Sprint s WHERE s.sprintPK.sprintid = :sprintid")
    , @NamedQuery(name = "Sprint.findByProjectid", query = "SELECT s FROM Sprint s WHERE s.sprintPK.projectid = :projectid")
    , @NamedQuery(name = "Sprint.findByStartdate", query = "SELECT s FROM Sprint s WHERE s.startdate = :startdate")
    , @NamedQuery(name = "Sprint.findByEnddate", query = "SELECT s FROM Sprint s WHERE s.enddate = :enddate")})
public class Sprint implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SprintPK sprintPK;
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @JoinColumn(name = "PROJECTID", referencedColumnName = "PROJECTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @OneToMany(mappedBy = "sprint")
    private Collection<Issue> issueCollection;

    public Sprint() {
    }

    public Sprint(SprintPK sprint03PK) {
        this.sprintPK = sprint03PK;
    }

    public Sprint(BigInteger sprintid, BigInteger projectid) {
        this.sprintPK = new SprintPK(sprintid, projectid);
    }

    public SprintPK getSprint03PK() {
        return sprintPK;
    }

    public void setSprint03PK(SprintPK sprint03PK) {
        this.sprintPK = sprint03PK;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Project getProject03() {
        return project;
    }

    public void setProject03(Project project03) {
        this.project = project03;
    }

    @XmlTransient
    public Collection<Issue> getIssue03Collection() {
        return issueCollection;
    }

    public void setIssue03Collection(Collection<Issue> issue03Collection) {
        this.issueCollection = issue03Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprintPK != null ? sprintPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) object;
        if ((this.sprintPK == null && other.sprintPK != null) || (this.sprintPK != null && !this.sprintPK.equals(other.sprintPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Sprint[ sprint03PK=" + sprintPK + " ]";
    }
    
}
