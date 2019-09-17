package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Customer;

@Path("/test")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Tets {

	@Context
	HttpServletRequest request;
	HttpServletResponse response;
	
	@GET
	@Path("/test")
	public void test(Customer customer) {
		System.out.println("aaaaa");
	}
}
