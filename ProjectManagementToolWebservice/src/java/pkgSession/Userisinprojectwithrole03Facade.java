/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgSession;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pkgEntities.Userisinprojectwithrole03;

/**
 *
 * @author alexk
 */
@Stateless
public class Userisinprojectwithrole03Facade extends AbstractFacade<Userisinprojectwithrole03> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Userisinprojectwithrole03Facade() {
        super(Userisinprojectwithrole03.class);
    }
    
}
