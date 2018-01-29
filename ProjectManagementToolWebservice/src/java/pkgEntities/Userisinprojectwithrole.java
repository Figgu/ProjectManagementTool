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
    @NamedQuery(name = "Userisinprojectwithrole.findAll", query = "SELECT u FROM Userisinprojectwithrole u")
    , @NamedQuery(name = "Userisinprojectwithrole.findByUserid", query = "SELECT u FROM Userisinprojectwithrole u WHERE u.userisinprojectwithrolePK.userid = :userid")
    , @NamedQuery(name = "Userisinprojectwithrole.findByProjectid", query = "SELECT u FROM Userisinprojectwithrole u WHERE u.userisinprojectwithrolePK.projectid = :projectid")})
public class Userisinprojectwithrole implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserisinprojectwithrolePK userisinprojectwithrolePK;
    @JoinColumn(name = "PROJECTID", referencedColumnName = "PROJECTID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Project project;
    @JoinColumn(name = "ROLEID", referencedColumnName = "ROLEID")
    @ManyToOne
    private Role roleid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Userisinprojectwithrole() {
    }

    public Userisinprojectwithrole(UserisinprojectwithrolePK userisinprojectwithrolePK) {
        this.userisinprojectwithrolePK = userisinprojectwithrolePK;
    }
    
    public Userisinprojectwithrole(User user, Role role) {
        this.user = user;
        this.roleid = role;
    }

    public Userisinprojectwithrole(BigInteger userid, BigInteger projectid) {
        this.userisinprojectwithrolePK = new UserisinprojectwithrolePK(userid, projectid);
    }

    public UserisinprojectwithrolePK getUserisinprojectwithrolePK() {
        return userisinprojectwithrolePK;
    }

    public void setUserisinprojectwithrolePK(UserisinprojectwithrolePK userisinprojectwithrolePK) {
        this.userisinprojectwithrolePK = userisinprojectwithrolePK;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project03) {
        this.project = project03;
    }

    public Role getRoleid() {
        return roleid;
    }

    public void setRoleid(Role roleid) {
        this.roleid = roleid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user03) {
        this.user = user03;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userisinprojectwithrolePK != null ? userisinprojectwithrolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userisinprojectwithrole)) {
            return false;
        }
        Userisinprojectwithrole other = (Userisinprojectwithrole) object;
        if ((this.userisinprojectwithrolePK == null && other.userisinprojectwithrolePK != null) || (this.userisinprojectwithrolePK != null && !this.userisinprojectwithrolePK.equals(other.userisinprojectwithrolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Userisinprojectwithrole[ userisinprojectwithrole03PK=" + userisinprojectwithrolePK + " ]";
    }
    
}
