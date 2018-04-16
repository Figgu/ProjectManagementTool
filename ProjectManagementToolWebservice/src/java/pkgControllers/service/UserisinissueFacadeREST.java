/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
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
import pkgControllers.service.AbstractFacade;
import pkgEntities.Userisinissue;
import pkgEntities.UserisinissuePK;
import javax.ws.rs.core.Response;
import pkgEntities.Issue;
import pkgEntities.User;
import pkgEntities.Userisinprojectwithrole;


/**
 *
 * @author alexk
 */
@Stateless
@Path("uii")
public class UserisinissueFacadeREST extends AbstractFacade<Userisinissue> {

    @PersistenceContext(unitName = "JPATestPU")
    private EntityManager em;

    private UserisinissuePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;userid=useridValue;issueid=issueidValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        pkgEntities.UserisinissuePK key = new pkgEntities.UserisinissuePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> userid = map.get("userid");
        if (userid != null && !userid.isEmpty()) {
            key.setUserid(new java.math.BigInteger(userid.get(0)));
        }
        java.util.List<String> issueid = map.get("issueid");
        if (issueid != null && !issueid.isEmpty()) {
            key.setIssueid(new java.math.BigInteger(issueid.get(0)));
        }
        return key;
    }

    public UserisinissueFacadeREST() {
        super(Userisinissue.class);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createUser(String usersStr) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Userisinissue>>() {}.getType();
        ArrayList<Userisinissue> users = gson.fromJson(usersStr, type);
        
        for (Userisinissue uii : users) {
            uii.setUserisinissuePK(new UserisinissuePK(uii.getUser().getUserid().toBigInteger(), uii.getIssue().getIssueid().toBigInteger()));
            super.create(uii);
        }
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public String getUsersOfIssue(String issueStr) {
        Issue issue = new Gson().fromJson(issueStr, Issue.class);
        ArrayList<User> users = new ArrayList<>();
        
        for (Userisinissue uii : super.findAll()) {
            if (uii.getIssue().getIssueid().toPlainString().equals(issue.getIssueid().toPlainString())) {
                users.add(uii.getUser());
            }
        }
        
        return new Gson().toJson(users);
    }
    /*
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Userisinissue entity) {
        super.create(entity);
    }
*/

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Userisinissue entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        pkgEntities.UserisinissuePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Userisinissue find(@PathParam("id") PathSegment id) {
        pkgEntities.UserisinissuePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Userisinissue> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Userisinissue> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
