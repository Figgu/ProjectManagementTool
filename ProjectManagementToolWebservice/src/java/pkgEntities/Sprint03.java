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
    @NamedQuery(name = "Sprint03.findAll", query = "SELECT s FROM Sprint03 s")
    , @NamedQuery(name = "Sprint03.findBySprintid", query = "SELECT s FROM Sprint03 s WHERE s.sprint03PK.sprintid = :sprintid")
    , @NamedQuery(name = "Sprint03.findByProjectid", query = "SELECT s FROM Sprint03 s WHERE s.sprint03PK.projectid = :projectid")
    , @NamedQuery(name = "Sprint03.findByStartdate", query = "SELECT s FROM Sprint03 s WHERE s.startdate = :startdate")
    , @NamedQuery(name = "Sprint03.findByEnddate", query = "SELECT s FROM Sprint03 s WHERE s.enddate = :enddate")})
public class Sprint03 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected Sprint03PK sprint03PK;
    @Column(name = "STARTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    @Column(name = "ENDDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    @JoinColumn(name = "PROJECTID", referencedColumnName = "PROJECTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project03 project03;
    @OneToMany(mappedBy = "sprint03")
    private Collection<Issue03> issue03Collection;

    public Sprint03() {
    }

    public Sprint03(Sprint03PK sprint03PK) {
        this.sprint03PK = sprint03PK;
    }

    public Sprint03(BigInteger sprintid, BigInteger projectid) {
        this.sprint03PK = new Sprint03PK(sprintid, projectid);
    }

    public Sprint03PK getSprint03PK() {
        return sprint03PK;
    }

    public void setSprint03PK(Sprint03PK sprint03PK) {
        this.sprint03PK = sprint03PK;
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

    public Project03 getProject03() {
        return project03;
    }

    public void setProject03(Project03 project03) {
        this.project03 = project03;
    }

    @XmlTransient
    public Collection<Issue03> getIssue03Collection() {
        return issue03Collection;
    }

    public void setIssue03Collection(Collection<Issue03> issue03Collection) {
        this.issue03Collection = issue03Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprint03PK != null ? sprint03PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprint03)) {
            return false;
        }
        Sprint03 other = (Sprint03) object;
        if ((this.sprint03PK == null && other.sprint03PK != null) || (this.sprint03PK != null && !this.sprint03PK.equals(other.sprint03PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Sprint03[ sprint03PK=" + sprint03PK + " ]";
    }
    
}
