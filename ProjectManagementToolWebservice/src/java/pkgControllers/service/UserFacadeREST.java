/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import com.google.gson.Gson;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgEntities.User;



/**
 *
 * @author alexk
 */
@Stateless
@Path("users")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String entity) {
        User user = new Gson().fromJson(entity, User.class);
        System.out.println(user.getProfilepicture());
        List<User> users = super.findAll();
        boolean emailExists = false;
        boolean UsernameExists = false;
        Response response = Response.ok().entity("User created successfully").build();

        
        for (User u : users) {                
            if (u.getUsername().equals(user.getUsername())) {
                response = Response.status(400).entity("A User with given Username already exists").build();
                UsernameExists = true;
            } 
            
            if (u.getEmail().equals(user.getEmail())) {
                response = Response.status(400).entity("A User with given Email already exists").build();
                emailExists = true;
            }
        }

        if (!emailExists && !UsernameExists) {
            super.create(user);
        } else {
            response = Response.status(400).entity("A User with given Username and Email already exists").build();
        }
    
        return response;
    }
    
    //For Login -> checks the given username & password
    @POST
    @Path("isValid")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response isValid(User user) {
        Response response = null;
        boolean exists = false;
        User givenUser = null;
        List<User> users = super.findAll();
        Gson gson = new Gson();
        
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                exists = true;                    
                givenUser = u;
            }                
        }

        if (exists) {
            if (givenUser.getUsername().equals(user.getUsername()) && givenUser.getPassword().equals(user.getPassword())) {
                response = Response.ok().entity(gson.toJson(givenUser)).build();
            } else {
                response = Response.status(400).entity("Given Password not correct").build();
            }                
        } else {
            response = Response.status(400).entity("User with given Username does not exist").build();
        }
        
        
        return response;
    }
    
    @GET
    @Path("{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userExists(@PathParam("username") String username) {
        List<User> users = super.findAll();
        Response response = Response.status(400).build();
;
        
        for (User user : users) {
            if(user.getUsername().equals(username)) {
                response =  Response.ok().entity(user).build();
            }
        }
        
        return response;
    }
    
    @POST
    @Path("forgotPW")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void forgotPW(String email) {
        /*
      String to = email;
      String from = "web@gmail.com";
      String host = "localhost";
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", host);
      Session session = Session.getDefaultInstance(properties);

      try {
         MimeMessage message = new MimeMessage(session);
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject("This is the Subject Line!");
         message.setText("This is actual message");
         Transport.send(message);
         System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }
*/
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response edit(@PathParam("id") BigDecimal id, User entity) {
        List<User> users = super.findAll();
        boolean everythingOK = true;
        Response response = Response.ok().entity("Information successfully updated").build();
        
        System.out.println("--id: " +entity.getUserid());
        System.out.println("--name: "+entity.getUsername());
        System.out.println("--email: "+entity.getEmail());
                    
        for (User u : users) {  
            if (u.getUsername().equals(entity.getUsername())) {
                if (!u.getUserid().toPlainString().equals(entity.getUserid().toPlainString())) {
                    response = Response.status(400).entity("A user with the given name already exists").build();
                    everythingOK = false;
                }

            } 
            
            if (u.getEmail().equals(entity.getEmail())) {
                System.out.println("--userid:" + u.getUserid());
                if (!u.getUserid().toPlainString().equals(entity.getUserid().toPlainString())) {
                    response = Response.status(400).entity("A user with the given email already exists").build();
                    everythingOK = false;
                }
            }
        }

        if (everythingOK) {
            super.edit(entity);               
        }
        
        return response;     
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAllUsers() {    
        return new Gson().toJson(super.findAll());
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
