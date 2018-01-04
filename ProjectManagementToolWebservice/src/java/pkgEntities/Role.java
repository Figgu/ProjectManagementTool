/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgEntities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexk
 */
@Entity
@Table(name = "ROLE03", catalog = "", schema = "D5B03")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
    , @NamedQuery(name = "Role.findByRoleid", query = "SELECT r FROM Role r WHERE r.roleid = :roleid")
    , @NamedQuery(name = "Role.findByName", query = "SELECT r FROM Role r WHERE r.name = :name")
    , @NamedQuery(name = "Role.findByDescription", query = "SELECT r FROM Role r WHERE r.description = :description")
    , @NamedQuery(name = "Role.findByIsunique", query = "SELECT r FROM Role r WHERE r.isunique = :isunique")})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ROLEID")
    private BigDecimal roleid;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ISUNIQUE")
    private String isunique;
    @OneToMany(mappedBy = "roleid")
    private Collection<Userisinprojectwithrole> userisinprojectwithroleCollection;

    public Role() {
    }

    public Role(BigDecimal roleid) {
        this.roleid = roleid;
    }

    public BigDecimal getRoleid() {
        return roleid;
    }

    public void setRoleid(BigDecimal roleid) {
        this.roleid = roleid;
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

    public String getIsunique() {
        return isunique;
    }

    public void setIsunique(String isunique) {
        this.isunique = isunique;
    }

    @XmlTransient
    public Collection<Userisinprojectwithrole> getUserisinprojectwithrole03Collection() {
        return userisinprojectwithroleCollection;
    }

    public void setUserisinprojectwithrole03Collection(Collection<Userisinprojectwithrole> userisinprojectwithrole03Collection) {
        this.userisinprojectwithroleCollection = userisinprojectwithrole03Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleid != null ? roleid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleid == null && other.roleid != null) || (this.roleid != null && !this.roleid.equals(other.roleid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Role[ roleid=" + roleid + " ]";
    }
    
}
