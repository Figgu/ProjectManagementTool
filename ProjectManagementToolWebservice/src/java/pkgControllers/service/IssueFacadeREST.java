/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import pkgEntities.Issue;
import javax.ws.rs.core.Response;
import pkgEntities.SprintPK;


/**
 *
 * @author Figgu
 */
@Stateless
@Path("issues")
public class IssueFacadeREST extends AbstractFacade<Issue> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    public IssueFacadeREST() {
        super(Issue.class);
    }

    //ID of sprint has to be delivered via path param to work around stackoverflow exception
    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createIssue(@PathParam("id") String id, String entity) {
        Issue issue = new Gson().fromJson(entity, Issue.class);
        System.out.println("--name: " + issue.getName());
        System.out.println("--descrption: " + issue.getDescription());
        System.out.println("--status: " + issue.getStatus());
        issue.getSprint().setSprintPK(new SprintPK(issue.getSprint().getSprintid().toBigInteger(), new BigInteger(id)));
        Response response = null;
        Collection<Issue> issues = super.findAll();
        boolean ok = true;
        
        for (Issue is : issues) {
            if (issue.getName().equals(is.getName())) {
                response = Response.status(400).entity("Issue with given name already exists").build();
                ok = false;
            }
        }
        
        if (ok) {
            super.create(issue);
            issues = super.findAll();
            Issue myIssue = null;
            
            for(Issue is : issues) {
                if (is.getName().equals(issue.getName())) {
                    myIssue = is;
                }
            }
            
            System.out.println("--"+myIssue.getIssueid());
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            System.out.println("--"+gson.toJson(myIssue));
            response = Response.ok(gson.toJson(myIssue)).build();
        }
                
        return response;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") BigDecimal id, Issue entity) {
        super.edit(entity);
    }

    @DELETE
    public void remove(@PathParam("id") BigDecimal id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("find")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Issue find(@PathParam("id") BigDecimal id) {
        return super.find(id);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Issue> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
