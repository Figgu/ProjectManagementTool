/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgEntities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author alexk
 */
@Embeddable
public class UserisinissuePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "USERID")
    private BigInteger userid;
    @Basic(optional = false)
    @Column(name = "ISSUEID")
    private BigInteger issueid;

    public UserisinissuePK() {
    }

    public UserisinissuePK(BigInteger userid, BigInteger issueid) {
        this.userid = userid;
        this.issueid = issueid;
    }

    public BigInteger getUserid() {
        return userid;
    }

    public void setUserid(BigInteger userid) {
        this.userid = userid;
    }

    public BigInteger getIssueid() {
        return issueid;
    }

    public void setIssueid(BigInteger issueid) {
        this.issueid = issueid;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.userid);
        hash = 79 * hash + Objects.hashCode(this.issueid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserisinissuePK other = (UserisinissuePK) obj;
        if (!Objects.equals(this.userid, other.userid)) {
            return false;
        }
        if (!Objects.equals(this.issueid, other.issueid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserisinissuePK{" + "userid=" + userid + ", issueid=" + issueid + '}';
    }
    
    
}
