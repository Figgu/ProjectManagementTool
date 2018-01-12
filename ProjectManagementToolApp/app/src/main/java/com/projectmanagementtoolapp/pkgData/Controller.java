package com.projectmanagementtoolapp.pkgData;

import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.multipart.impl.MultiPartWriter;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Created by alexk on 08.01.2018.
 */

public class Controller {
    private static Controller controller;

    private String uri = null;
    private ClientConfig config = null;
    private Client client = null;
    private WebResource service = null;

    public static Controller getInstance() {
        if (controller == null)
            controller = new Controller("http://localhost:64634/ProjectManagementToolWebservice/webresources/");

        return controller;
    }

    public Controller(String _uri) {
        setUri(_uri);
    }

    public String isValid(User user) {
        String retVal = service.path("isValid")
                .accept(MediaType.APPLICATION_JSON).post(String.class, user);
        System.out.println(retVal);

        JsonObject jsonObject = new JsonObject();
        String key = jsonObject.get("message").getAsString();

        return key;
    }


    public void setUri(String uri) {
        this.uri = uri;
        config = new DefaultClientConfig();
        config.getClasses().add(MultiPartWriter.class);

        client = Client.create(config);
        service = client.resource(getBaseURI());
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri(uri).build();
    }
}
