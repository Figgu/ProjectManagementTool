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
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByUserid", query = "SELECT u FROM User u WHERE u.userid = :userid")
    , @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
public class User implements Serializable {

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
    private byte[] profilepicture;
    @ManyToMany(mappedBy = "userCollection")
    private transient Collection<Issue> issueCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private transient Collection<Userisinprojectwithrole> userisinprojectwithroleCollection;

    public User() {
    }

    public User(BigDecimal userid) {
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

    public byte[] getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(byte[] profilepicture) {
        this.profilepicture = profilepicture;
    }

    @XmlTransient
    public Collection<Issue> getIssueCollection() {
        return issueCollection;
    }

    public void setIssueCollection(Collection<Issue> issue03Collection) {
        this.issueCollection = issue03Collection;
    }

    @XmlTransient
    public Collection<Userisinprojectwithrole> getUserisinprojectwithroleCollection() {
        return userisinprojectwithroleCollection;
    }

    public void setUserisinprojectwithroleCollection(Collection<Userisinprojectwithrole> userisinprojectwithrole03Collection) {
        this.userisinprojectwithroleCollection = userisinprojectwithrole03Collection;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "userid=" + userid + ", username=" + username + ", password=" + password + ", email=" + email + '}';
    }

    
}
