/*
 * To change this license header, choose License Headers in Role Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.Iterator;
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
import pkgEntities.Role;
import pkgEntities.Userisinprojectwithrole;


/**
 *
 * @author alexk
 */
@Stateless
@Path("roles")
public class RoleFacadeREST extends AbstractFacade<Role> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    public RoleFacadeREST() {
        super(Role.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRole(Role entity) {       
        List<Role> roles = super.findAll();
        Response response = Response.ok().entity("Role created successfully").build();
        boolean roleExists = true;
        
        for (Role role : roles) {
            if (role.getName().equals(entity.getName())) {
                response = Response.status(400).entity("A role with the given name already exists").build();
                roleExists = false;
            }
        }
        
        if (roleExists)
            super.create(entity);
        
        return response;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editRole(Role entity) {
        List<Role> roles = super.findAll();
        Response response = Response.ok().entity("Role successfully updated").build();
        boolean nameOK = true;
        
        for (Role role : roles) { 
            if (entity.getName().equals(role.getName()) && !role.getRoleid().toPlainString().equals(entity.getRoleid().toPlainString())) {
                response = Response.status(400).entity("A role with the given name already existst").build();
                nameOK = false;
            } 
        }

        if (nameOK)
            super.edit(entity);               
      
        return response;
    }

    @DELETE
    @Path("{id}")
    public Response remove(@PathParam("id") BigDecimal id) {
        List<Role> roles = super.findAll();
        Response response = Response.ok().entity("Role deleted successfully").build();
        boolean canDelete = true;
        
        for (Role r : roles) { 
            if (r.getUserisinprojectwithroleCollection() != null) {
                Collection<Userisinprojectwithrole> uprs = r.getUserisinprojectwithroleCollection();
            
                for (Userisinprojectwithrole u : uprs) {
                    if(u.getRoleid() != null) {
                        if(u.getRoleid().equals(r)) {
                            response = Response.status(400).entity("You cant delete this role as it is in use right now").build();
                                                        canDelete = false;

                        }
                    }
                } 
                canDelete = false;
            }
        }

        if (canDelete)
            super.remove(super.find(id));                             
        
        return response;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Role find(@PathParam("id") BigDecimal id) {
        return super.find(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        return new Gson().toJson(super.findAll());
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Role> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
