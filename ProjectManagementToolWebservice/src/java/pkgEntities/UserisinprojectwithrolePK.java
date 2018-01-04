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
public class Userisinprojectwithrole03PK implements Serializable {

    @Basic(optional = false)
    @Column(name = "USERID")
    private BigInteger userid;
    @Basic(optional = false)
    @Column(name = "PROJECTID")
    private BigInteger projectid;

    public Userisinprojectwithrole03PK() {
    }

    public Userisinprojectwithrole03PK(BigInteger userid, BigInteger projectid) {
        this.userid = userid;
        this.projectid = projectid;
    }

    public BigInteger getUserid() {
        return userid;
    }

    public void setUserid(BigInteger userid) {
        this.userid = userid;
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
        hash += (userid != null ? userid.hashCode() : 0);
        hash += (projectid != null ? projectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userisinprojectwithrole03PK)) {
            return false;
        }
        Userisinprojectwithrole03PK other = (Userisinprojectwithrole03PK) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        if ((this.projectid == null && other.projectid != null) || (this.projectid != null && !this.projectid.equals(other.projectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Userisinprojectwithrole03PK[ userid=" + userid + ", projectid=" + projectid + " ]";
    }
    
}
