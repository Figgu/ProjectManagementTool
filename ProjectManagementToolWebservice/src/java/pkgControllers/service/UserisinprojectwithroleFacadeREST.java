/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import pkgEntities.Project;
import pkgEntities.Userisinprojectwithrole;
import pkgEntities.UserisinprojectwithrolePK;

/**
 *
 * @author alexk
 */
@Stateless
@Path("upr")
public class UserisinprojectwithroleFacadeREST extends AbstractFacade<Userisinprojectwithrole> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    private UserisinprojectwithrolePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;userid=useridValue;projectid=projectidValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        pkgEntities.UserisinprojectwithrolePK key = new pkgEntities.UserisinprojectwithrolePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> userid = map.get("userid");
        if (userid != null && !userid.isEmpty()) {
            key.setUserid(new java.math.BigInteger(userid.get(0)));
        }
        java.util.List<String> projectid = map.get("projectid");
        if (projectid != null && !projectid.isEmpty()) {
            key.setProjectid(new java.math.BigInteger(projectid.get(0)));
        }
        return key;
    }

    public UserisinprojectwithroleFacadeREST() {
        super(Userisinprojectwithrole.class);
    }

    /*
    @POST
    @Override
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(Userisinprojectwithrole entity) {
        super.create(entity);
    }
*/
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUsersInProject(String usersStr) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Userisinprojectwithrole>>() {}.getType();
        ArrayList<Userisinprojectwithrole> users = gson.fromJson(usersStr, type);
        
        for (Userisinprojectwithrole user : users) {
            System.out.println("--user: " + user.getUser());
            System.out.println("--project: " + user.getProject());
            user.setUserisinprojectwithrolePK(new UserisinprojectwithrolePK(user.getUser().getUserid().toBigInteger(), user.getProject().getProjectid().toBigInteger()));
            super.create(user);
        }
    }
    
    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUsersInProject(@PathParam("id")int id) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Userisinprojectwithrole>>() {}.getType();
        Collection<Userisinprojectwithrole> users = super.findAll();              
      
        for (Userisinprojectwithrole user : users) {
            if(user.getProject().getProjectid().toPlainString().equals(String.valueOf(id))) {
                super.remove(user);
            }
        }
    }
    

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Userisinprojectwithrole entity) {
        super.edit(entity);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Userisinprojectwithrole find(@PathParam("id") PathSegment id) {
        pkgEntities.UserisinprojectwithrolePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Userisinprojectwithrole> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Userisinprojectwithrole> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
