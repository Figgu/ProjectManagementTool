/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgSession;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pkgEntities.Project03;

/**
 *
 * @author alexk
 */
@Stateless
public class Project03Facade extends AbstractFacade<Project03> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Project03Facade() {
        super(Project03.class);
    }
    
}
