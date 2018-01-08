/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import java.math.BigDecimal;
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
import org.json.JSONException;
import org.json.JSONObject;
import pkgEntities.Project;

/**
 *
 * @author alexk
 */
@Stateless
@Path("project")
public class ProjectFacadeREST extends AbstractFacade<Project> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    public ProjectFacadeREST() {
        super(Project.class);
    }

    @POST
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String createProject(Project entity) throws JSONException {
        JSONObject response = new JSONObject();
        response.put("message", "Proejct created");
        List<Project> projects = super.findAll();
        boolean projectOK = true;
        
        for (Project project : projects) {
            if (project.getName().equals(entity.getName())) {
                response.put("message", "Project with the given name already exists");
                projectOK = false;
            }
        }
        
        if (projectOK)
            super.create(entity);
        
        return response.toString();
    }

    @PUT
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String edit(@PathParam("id") BigDecimal id, Project entity) {
        List<Project> projects = super.findAll();
        boolean projectExists = false;
        JSONObject response = new JSONObject();
        response.put("message", "Project created");
        Project project = super.find(id);
        for (Project p : projects) { 
            if (p.getName().equals(project.getName())) {
                response.put("message", "Project updated");
                projectExists = true;
            } 
        }

        if (projectExists) {
            super.edit(entity);               
        } else {
            response.put("message", "There is no such project");
        }

        
        return response.toString();
    }

    @DELETE
    @Path("remove")
    public String remove(@PathParam("id") BigDecimal id) {
        JSONObject response = new JSONObject();
        response.put("message", "empty");
        List<Project> projects = super.findAll();
        Boolean projectExists = false;
        Project project = super.find(id);
        for (Project p : projects) {                
            if (p.getName().equals(project.getName())) {
                response.put("message", "Project exists");
                projectExists = true;
            } 
        }

        if (projectExists) {
            super.remove(super.find(id));              
        } else {
            response.put("message", "There is no such project");
        }
        return response.toString();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Project find(@PathParam("id") BigDecimal id) {
        return super.find(id);
    }

    @GET
    @Path("getAll")
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Project> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Project> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
