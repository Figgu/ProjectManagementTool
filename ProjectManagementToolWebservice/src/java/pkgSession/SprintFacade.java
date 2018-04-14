/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgSession;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pkgEntities.Sprint;

/**
 *
 * @author alexk
 */
@Stateless
public class SprintFacade extends AbstractFacade<Sprint> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprintFacade() {
        super(Sprint.class);
    }
    
}
