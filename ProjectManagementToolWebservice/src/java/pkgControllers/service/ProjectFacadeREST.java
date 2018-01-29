/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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
import javax.ws.rs.core.Response;
import pkgEntities.Project;
import pkgEntities.Sprint;
import pkgEntities.Userisinprojectwithrole;
import pkgEntities.UserisinprojectwithrolePK;

/**
 *
 * @author Figgu
 */
@Stateless
@Path("projects")
public class ProjectFacadeREST extends AbstractFacade<Project>{

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    public ProjectFacadeREST() {
        super(Project.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String entity) {
        Project project = new Gson().fromJson(entity, Project.class);
        List<Project> projects = super.findAll();
        Response response = Response.ok().build();
        
        for (Project p : projects) {
            if (p.getName().equals(project.getName())) {
                response = Response.status(400).entity("A project with the given name already exists").build();
                return response;
            }
        }
        
        super.create(project);
        projects = super.findAll();
        Project newProject = null;

        for (Project p : projects) {
            if (p.getName().equals(project.getName())) {
                newProject = p;
            }
        }
        return Response.ok().entity(new Gson().toJson(newProject)).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") BigDecimal id, Project entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") BigDecimal id) {
        super.remove(super.find(id));
    }

    //Get projects with id of user in it
    @POST
    @Path("fromUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> getProjectsFromUser(BigDecimal id) {
        List<Project> projects = super.findAll();
        ArrayList<Project> projectsOfUser = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Project>>() {}.getType();
               
        for (Project p : projects) {
            super.refresh(p);
        }
                
        for (Project p : projects) {
            Collection<Userisinprojectwithrole> users = p.getUserisinprojectwithroleCollection();         
            
            if (users.size() > 1) {
                for (Userisinprojectwithrole u : users) {               
                    if (u.getUser().getUserid().equals(id)) {
                        projectsOfUser.add(p);
                    }
                }  
            }
        }
    
        return projectsOfUser;
    }
    
    @GET
    @Path("sprints/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSprintsFromProject(@PathParam("id") BigDecimal id) {
        List<Project> projects = super.findAll();
        Collection<Sprint> sprintsOfProject = null;
        ArrayList<Sprint> retValue = new ArrayList<>();
               
        for (Project p : projects) {
            super.refresh(p);
        }     
                
        for (Project p : projects) {
            if (p.getProjectid().toPlainString().equals(String.valueOf(id))) {
                sprintsOfProject = p.getSprintCollection();
                
                for (Sprint sprint : sprintsOfProject) {
                    sprint.setProject(null);
                    retValue.add(sprint);                  
                }
            }
        }
  
        return new Gson().toJson(retValue);
    }
    
    @GET
    @Path("users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsersFromProject(@PathParam("id") int projectid) {
        List<Project> projects = super.findAll();
        ArrayList<Userisinprojectwithrole> usersOfProject = new ArrayList<>();
        System.out.println("--givenid: " + projectid);

                        
        for (Project p : projects) {
            Collection<Userisinprojectwithrole> users = p.getUserisinprojectwithroleCollection();         

            for (Userisinprojectwithrole u : users) {       
                System.out.println("--project: " + u.getProject());
                if (u.getProject().getProjectid().toPlainString().equals(String.valueOf(projectid))) {
                    System.out.println("--user in project: " + u.getUser());
                    usersOfProject.add(new Userisinprojectwithrole(u.getUser(), u.getRoleid()));
                }
            }  
        }
        
        return new Gson().toJson(usersOfProject);
    }

    @GET
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
