package services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.view.Viewable;

@Path("/")
public class JaxrsTestClass {


    @GET
    @Produces("text/html")
    public Response index() {
        return Response.ok(new Viewable("/index.jsp")).build();
    }
    
    @GET
    @Path("index")
    public Viewable index(@Context HttpServletRequest request) {
        request.setAttribute("obj", new String("IT Works"));
        System.out.println("/INDEX called");
        return new Viewable("/index.jsp", null);
    }
	
}
