/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pkgData.User;

/**
 *
 * @author Figgu
 */
@Path("User")
public class UserController {
   
    @Context
    private UriInfo context;
    
    public UserController() {
    }

    @POST
    @Path("currentUser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String isValid(@FormParam("id") String id,
      @FormParam("username") String username,
      @FormParam("password") String password,
      @FormParam("email") String email,
      @Context HttpServletResponse servletResponse) throws IOException{
      User user = new User(id, username, password, email);
      
      return user.getUsername() + " " + user.getPassword();
    }
    
}
