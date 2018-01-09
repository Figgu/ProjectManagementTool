/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

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
import org.json.JSONException;
import org.json.JSONObject;
import pkgEntities.Sprint;
import pkgEntities.SprintPK;

/**
 *
 * @author Figgu
 */
@Stateless
@Path("pkgentities.sprint")
public class SprintFacadeREST extends AbstractFacade<Sprint> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    private SprintPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;sprintid=sprintidValue;projectid=projectidValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        pkgEntities.SprintPK key = new pkgEntities.SprintPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> sprintid = map.get("sprintid");
        if (sprintid != null && !sprintid.isEmpty()) {
            key.setSprintid(new java.math.BigInteger(sprintid.get(0)));
        }
        java.util.List<String> projectid = map.get("projectid");
        if (projectid != null && !projectid.isEmpty()) {
            key.setProjectid(new java.math.BigInteger(projectid.get(0)));
        }
        return key;
    }

    public SprintFacadeREST() {
        super(Sprint.class);
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createSprint(Sprint entity) throws JSONException{
        JSONObject response = new JSONObject();
        response.put("message", "Proejct created");
        List<Sprint> sprints = super.findAll();
        boolean sprintDateOK = true;
        boolean projectExists = false;
        
        for (Sprint sprint : sprints) {
            if (sprint.getStartdate().compareTo(entity.getEnddate()) > 0) {
                response.put("message", "New Sprint can't be before ended Sprint");
                sprintDateOK = false;
            }
            if(sprint.getProject03().getName().equals(entity.getProject03().getName()))
            {
                projectExists = true;
            }
        }
        
        if (sprintDateOK)
            super.create(entity);
        
        return response.toString();
    }

    @PUT
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Sprint entity) {
        /*List<Sprint> sprints = super.findAll();
        boolean sprintExists = false;
        JSONObject response = new JSONObject();
        response.put("message", "Sprint created");
        Sprint role = super.find(id);
        for (Sprint p : sprints) { 
            if (p.getName().equals(sprint.getName())) {
                response.put("message", "Sprint updated");
                sprintExists = true;
            } 
        }

        if (sprintExists) {
            super.edit(entity);               
        } else {
            response.put("message", "There is no such sprint");
        }

        
        return response.toString();*/
    }

    @DELETE
    @Path("remove")
    public void remove(@PathParam("id") PathSegment id) {
        pkgEntities.SprintPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("find")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Sprint find(@PathParam("id") PathSegment id) {
        pkgEntities.SprintPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Path("getAll")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprint> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Sprint> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
