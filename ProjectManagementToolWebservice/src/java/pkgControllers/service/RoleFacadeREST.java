/*
 * To change this license header, choose License Headers in Role Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import java.math.BigDecimal;
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
import org.json.JSONException;
import org.json.JSONObject;
import pkgEntities.Role;


/**
 *
 * @author alexk
 */
@Stateless
@Path("role")
public class RoleFacadeREST extends AbstractFacade<Role> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    public RoleFacadeREST() {
        super(Role.class);
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createRole(Role entity) throws JSONException {
        JSONObject response = new JSONObject();
        response.put("message", "Proejct created");
        List<Role> roles = super.findAll();
        boolean roleExists = true;
        
        for (Role role : roles) {
            if (role.getName().equals(entity.getName())) {
                response.put("message", "Role with the given name already exists");
                roleExists = false;
            }
        }
        
        if (roleExists)
            super.create(entity);
        
        return response.toString();
    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String edit(@PathParam("id") BigDecimal id, Role entity) throws JSONException{
        List<Role> roles = super.findAll();
        boolean roleExists = false;
        JSONObject response = new JSONObject();
        response.put("message", "Role created");
        Role role = super.find(id);
        for (Role p : roles) { 
            if (p.getName().equals(role.getName())) {
                response.put("message", "Role updated");
                roleExists = true;
            } 
        }

        if (roleExists) {
            super.edit(entity);               
        } else {
            response.put("message", "There is no such role");
        }

        
        return response.toString();
    }

    @DELETE
    @Path("remove")
    public String remove(@PathParam("id") BigDecimal id) throws JSONException{
        JSONObject response = new JSONObject();
        response.put("message", "empty");
        List<Role> roles = super.findAll();
        Boolean roleExists = false;
        Role role = super.find(id);
        
        for (Role u : roles) {                
            if (u.getName().equals(role.getName())) {
                response.put("message", "Role exists");
                roleExists = true;
            } 
        }

        if (roleExists) {
            super.remove(super.find(id));              
        } else {
            response.put("message", "There is no such role");
        }
        return response.toString();
    }

    @GET
    @Path("find")
    @Produces(MediaType.APPLICATION_JSON)
    public Role find(@PathParam("id") BigDecimal id) {
        return super.find(id);
    }

    @GET
    @Path("getAll")
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Role> findAll() {
        return super.findAll();
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
