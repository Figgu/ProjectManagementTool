/*
 * To change this license header, choose License Headers in Project Properties.
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
import pkgEntities.User;

/**
 *
 * @author alexk
 */
@Stateless
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(User entity) throws JSONException {
        List<User> users = super.findAll();
        boolean emailExists = false;
        boolean UsernameExists = false;
        JSONObject response = new JSONObject();
        response.put("message", "User created");

        for (User user : users) {                
            if (user.getUsername().equals(entity.getUsername())) {
                response.put("message", "Given Username already exists");
                UsernameExists = true;
            } else if (user.getEmail().equals(entity.getEmail())) {
                response.put("message", "Given Email already exists");
                emailExists = true;
            }
        }

        if (!emailExists && !UsernameExists) {
            super.create(entity);                
        } else {
            response.put("message", "Given Username and Email already exists");
        }

        
        return response.toString();
    }
    
    //For Login -> checks the given username & password
    @POST
    @Path("isValid")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String isValid(User user) throws JSONException {
        JSONObject response = new JSONObject();
        response.put("message", "empty");
        boolean exists = false;
        User givenUser = null;
        List<User> users = super.findAll();

        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                exists = true;                    
                givenUser = u;
            }                
        }

        if (exists) {
            if (givenUser.getUsername().equals(user.getUsername()) && givenUser.getPassword().equals(user.getPassword())) {
                response.put("message", "ok");
            } else {
                response.put("message", "Given Password not correct");
            }                
        } else {
            response.put("message", "User with given Username does not exist");
        }
        
        return response.toString();
    }
    

    @DELETE
    @Path("remove")
    public String remove(@PathParam("id") BigDecimal id) throws JSONException{
        JSONObject response = new JSONObject();
        response.put("message", "empty");
        List<User> users = super.findAll();
        Boolean userExists = false;
        User user = super.find(id);
        
        for (User u : users) {                
            if (u.getUsername().equals(user.getUsername())) {
                response.put("message", "User exists");
                userExists = true;
            } 
        }

        if (userExists) {
            super.remove(super.find(id));              
        } else {
            response.put("message", "There is no such user");
        }
        return response.toString();
        
    }
    
    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public String edit(@PathParam("id") BigDecimal id, User entity) throws JSONException{
        List<User> users = super.findAll();
        boolean userExists = false;
        JSONObject response = new JSONObject();
        User user = super.find(id);
        
        for (User u : users) {  
            if (u.getUsername().equals(user.getUsername())) {
                response.put("message", "User exists");
                userExists = true;
            } 
        }

        if (userExists) {
            super.edit(entity);               
        } else {
            response.put("message", "There is no such user");
        }

        
        return response.toString();
        
    }

    @GET
    @Path("find")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("id") BigDecimal id) {
        return super.find(id);
    }

    @GET
    @Path("getAll")
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
