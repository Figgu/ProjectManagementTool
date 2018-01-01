/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgEntities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author alexk
 */
@Embeddable
public class Sprint03PK implements Serializable {

    @Basic(optional = false)
    @Column(name = "SPRINTID")
    private BigInteger sprintid;
    @Basic(optional = false)
    @Column(name = "PROJECTID")
    private BigInteger projectid;

    public Sprint03PK() {
    }

    public Sprint03PK(BigInteger sprintid, BigInteger projectid) {
        this.sprintid = sprintid;
        this.projectid = projectid;
    }

    public BigInteger getSprintid() {
        return sprintid;
    }

    public void setSprintid(BigInteger sprintid) {
        this.sprintid = sprintid;
    }

    public BigInteger getProjectid() {
        return projectid;
    }

    public void setProjectid(BigInteger projectid) {
        this.projectid = projectid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprintid != null ? sprintid.hashCode() : 0);
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprint03PK)) {
            return false;
        }
        Sprint03PK other = (Sprint03PK) object;
        if ((this.sprintid == null && other.sprintid != null) || (this.sprintid != null && !this.sprintid.equals(other.sprintid))) {
            return false;
        }
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Sprint03PK[ sprintid=" + sprintid + ", projectid=" + projectid + " ]";
    }
    
}
