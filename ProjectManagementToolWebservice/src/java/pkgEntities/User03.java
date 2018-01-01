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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexk
 */
@Entity
@Table(name = "USER03", catalog = "", schema = "D5B03")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User03.findAll", query = "SELECT u FROM User03 u")
    , @NamedQuery(name = "User03.findByUserid", query = "SELECT u FROM User03 u WHERE u.userid = :userid")
    , @NamedQuery(name = "User03.findByUsername", query = "SELECT u FROM User03 u WHERE u.username = :username")
    , @NamedQuery(name = "User03.findByPassword", query = "SELECT u FROM User03 u WHERE u.password = :password")
    , @NamedQuery(name = "User03.findByEmail", query = "SELECT u FROM User03 u WHERE u.email = :email")})
public class User03 implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "USERID")
    private BigDecimal userid;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "EMAIL")
    private String email;
    @Lob
    @Column(name = "PROFILEPICTURE")
    private Byte[] profilepicture;
    @ManyToMany(mappedBy = "user03Collection")
    private Collection<Issue03> issue03Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user03")
    private Collection<Userisinprojectwithrole03> userisinprojectwithrole03Collection;

    public User03() {
    }

    public User03(BigDecimal userid) {
        this.userid = userid;
    }

    public BigDecimal getUserid() {
        return userid;
    }

    public void setUserid(BigDecimal userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte[] getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(Byte[] profilepicture) {
        this.profilepicture = profilepicture;
    }

    @XmlTransient
    public Collection<Issue03> getIssue03Collection() {
        return issue03Collection;
    }

    public void setIssue03Collection(Collection<Issue03> issue03Collection) {
        this.issue03Collection = issue03Collection;
    }

    @XmlTransient
    public Collection<Userisinprojectwithrole03> getUserisinprojectwithrole03Collection() {
        return userisinprojectwithrole03Collection;
    }

    public void setUserisinprojectwithrole03Collection(Collection<Userisinprojectwithrole03> userisinprojectwithrole03Collection) {
        this.userisinprojectwithrole03Collection = userisinprojectwithrole03Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User03)) {
            return false;
        }
        User03 other = (User03) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.User03[ userid=" + userid + " ]";
    }
    
}
