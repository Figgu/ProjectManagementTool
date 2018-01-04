/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgEntities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alexk
 */
@Entity
@Table(name = "USERISINPROJECTWITHROLE03", catalog = "", schema = "D5B03")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userisinprojectwithrole03.findAll", query = "SELECT u FROM Userisinprojectwithrole03 u")
    , @NamedQuery(name = "Userisinprojectwithrole03.findByUserid", query = "SELECT u FROM Userisinprojectwithrole03 u WHERE u.userisinprojectwithrole03PK.userid = :userid")
    , @NamedQuery(name = "Userisinprojectwithrole03.findByProjectid", query = "SELECT u FROM Userisinprojectwithrole03 u WHERE u.userisinprojectwithrole03PK.projectid = :projectid")})
public class Userisinprojectwithrole03 implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected Userisinprojectwithrole03PK userisinprojectwithrole03PK;
    @JoinColumn(name = "PROJECTID", referencedColumnName = "PROJECTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project03 project03;
    @JoinColumn(name = "ROLEID", referencedColumnName = "ROLEID")
    @ManyToOne
    private Role03 roleid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User03 user03;

    public Userisinprojectwithrole03() {
    }

    public Userisinprojectwithrole03(Userisinprojectwithrole03PK userisinprojectwithrole03PK) {
        this.userisinprojectwithrole03PK = userisinprojectwithrole03PK;
    }

    public Userisinprojectwithrole03(BigInteger userid, BigInteger projectid) {
        this.userisinprojectwithrole03PK = new Userisinprojectwithrole03PK(userid, projectid);
    }

    public Userisinprojectwithrole03PK getUserisinprojectwithrole03PK() {
        return userisinprojectwithrole03PK;
    }

    public void setUserisinprojectwithrole03PK(Userisinprojectwithrole03PK userisinprojectwithrole03PK) {
        this.userisinprojectwithrole03PK = userisinprojectwithrole03PK;
    }

    public Project03 getProject03() {
        return project03;
    }

    public void setProject03(Project03 project03) {
        this.project03 = project03;
    }

    public Role03 getRoleid() {
        return roleid;
    }

    public void setRoleid(Role03 roleid) {
        this.roleid = roleid;
    }

    public User03 getUser03() {
        return user03;
    }

    public void setUser03(User03 user03) {
        this.user03 = user03;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userisinprojectwithrole03PK != null ? userisinprojectwithrole03PK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userisinprojectwithrole03)) {
            return false;
        }
        Userisinprojectwithrole03 other = (Userisinprojectwithrole03) object;
        if ((this.userisinprojectwithrole03PK == null && other.userisinprojectwithrole03PK != null) || (this.userisinprojectwithrole03PK != null && !this.userisinprojectwithrole03PK.equals(other.userisinprojectwithrole03PK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Userisinprojectwithrole03[ userisinprojectwithrole03PK=" + userisinprojectwithrole03PK + " ]";
    }
    
}
