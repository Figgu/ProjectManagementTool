/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgEntities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
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
@Table(name = "USERISINISSUE03", catalog = "", schema = "D5B03")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userisinissue.findAll", query = "SELECT u FROM Userisinissue u")
    , @NamedQuery(name = "Userisinissue.findByUserid", query = "SELECT u FROM Userisinissue u WHERE u.userisinissuePK.userid = :userid")
    , @NamedQuery(name = "Userisinissue.findByIssueid", query = "SELECT u FROM Userisinissue u WHERE u.userisinissuePK.issueid = :issueid")})
public class Userisinissue implements Serializable{
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserisinissuePK userisinissuePK;
    @JoinColumn(name = "ISSUEID", referencedColumnName = "ISSUEID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Issue issue;   
    @JoinColumn(name = "USERID", referencedColumnName = "USERID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    
    public Userisinissue() {
        
    }

    public Userisinissue(UserisinissuePK userisinissuePK) {
        this.userisinissuePK = userisinissuePK;
    }

    public Userisinissue(Issue issue, User user) {
        this.issue = issue;
        this.user = user;
    }

    public Userisinissue(BigInteger userID, BigInteger issueID) {
        userisinissuePK = new UserisinissuePK(userID, issueID);
    }

    public UserisinissuePK getUserisinissuePK() {
        return userisinissuePK;
    }

    public void setUserisinissuePK(UserisinissuePK userisinissuePK) {
        this.userisinissuePK = userisinissuePK;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Userisinissue{" + "userisinissuePK=" + userisinissuePK + ", issue=" + issue + ", user=" + user + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.userisinissuePK);
        hash = 19 * hash + Objects.hashCode(this.issue);
        hash = 19 * hash + Objects.hashCode(this.user);
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
        final Userisinissue other = (Userisinissue) obj;
        if (!Objects.equals(this.userisinissuePK, other.userisinissuePK)) {
            return false;
        }
        if (!Objects.equals(this.issue, other.issue)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
    
    
}
