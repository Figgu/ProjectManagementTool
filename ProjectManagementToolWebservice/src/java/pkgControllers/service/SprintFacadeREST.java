/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import com.google.gson.Gson;
import java.math.BigInteger;
import java.util.ArrayList;
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
import javax.ws.rs.core.Response;
import pkgEntities.Project;
import pkgEntities.Sprint;
import pkgEntities.SprintPK;

/**
 *
 * @author Figgu
 */
@Stateless
@Path("sprints")
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSprint(String sprintStr) {
        Response response = Response.ok().build();      
        Sprint sprint = new Gson().fromJson(sprintStr, Sprint.class);
        System.out.println("--project: " + sprint.getProject());
        sprint.setSprintPK(new SprintPK(null, sprint.getProject().getProjectid().toBigInteger()));
        super.create(sprint);            

        return Response.ok().build();
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
    @Produces(MediaType.APPLICATION_JSON)
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
