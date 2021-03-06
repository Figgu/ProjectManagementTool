/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgSession;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pkgEntities.Userisinprojectwithrole;

/**
 *
 * @author alexk
 */
@Stateless
public class UserisinprojectwithroleFacade extends AbstractFacade<Userisinprojectwithrole> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserisinprojectwithroleFacade() {
        super(Userisinprojectwithrole.class);
    }
    
}
