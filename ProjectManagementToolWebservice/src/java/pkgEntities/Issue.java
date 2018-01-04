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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexk
 */
@Entity
@Table(name = "ISSUE03", catalog = "", schema = "D5B03")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Issue.findAll", query = "SELECT i FROM Issue i")
    , @NamedQuery(name = "Issue.findByIssueid", query = "SELECT i FROM Issue i WHERE i.issueid = :issueid")
    , @NamedQuery(name = "Issue.findByName", query = "SELECT i FROM Issue i WHERE i.name = :name")
    , @NamedQuery(name = "Issue.findByDescription", query = "SELECT i FROM Issue i WHERE i.description = :description")
    , @NamedQuery(name = "Issue.findByStatus", query = "SELECT i FROM Issue i WHERE i.status = :status")})
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ISSUEID")
    private BigDecimal issueid;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private String status;
    @JoinTable(name = "USERISINISSUE03", joinColumns = {
        @JoinColumn(name = "ISSUEID", referencedColumnName = "ISSUEID")}, inverseJoinColumns = {
        @JoinColumn(name = "USERID", referencedColumnName = "USERID")})
    @ManyToMany
    private Collection<User> userCollection;
    @JoinColumns({
        @JoinColumn(name = "SPRINTID", referencedColumnName = "SPRINTID")
        , @JoinColumn(name = "SPRINTPROJECTID", referencedColumnName = "PROJECTID")})
    @ManyToOne
    private Sprint sprint;

    public Issue() {
    }

    public Issue(BigDecimal issueid) {
        this.issueid = issueid;
    }

    public BigDecimal getIssueid() {
        return issueid;
    }

    public void setIssueid(BigDecimal issueid) {
        this.issueid = issueid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<User> getUser03Collection() {
        return userCollection;
    }

    public void setUser03Collection(Collection<User> user03Collection) {
        this.userCollection = user03Collection;
    }

    public Sprint getSprint03() {
        return sprint;
    }

    public void setSprint03(Sprint sprint03) {
        this.sprint = sprint03;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issueid != null ? issueid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Issue)) {
            return false;
        }
        Issue other = (Issue) object;
        if ((this.issueid == null && other.issueid != null) || (this.issueid != null && !this.issueid.equals(other.issueid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pkgEntities.Issue[ issueid=" + issueid + " ]";
    }
    
}
