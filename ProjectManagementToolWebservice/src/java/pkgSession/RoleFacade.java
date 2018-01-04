/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgSession;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pkgEntities.Role03;

/**
 *
 * @author alexk
 */
@Stateless
public class Role03Facade extends AbstractFacade<Role03> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Role03Facade() {
        super(Role03.class);
    }
    
}
