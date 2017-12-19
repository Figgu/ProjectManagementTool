/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author alexk
 */
@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String name;
    private String description;
    private boolean isUnique;

    public Role(String id, String name, String description, boolean isUnique) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isUnique = isUnique;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isIsUnique() {
        return isUnique;
    }

    public void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }
    
    
}
