package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.view.Viewable;

import beans.Company;
import beans.Customer;
import exceptions.CouponSystemException;
import facades.AdminFacade;

@Path("/admin")

public class AdminService {

	@Context
	HttpServletRequest request;
	HttpServletResponse response;



	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(GenericUser admin) throws CouponSystemWebException, CouponSystemException {
		System.out.println("inside login method");
		AdminFacade facade = new AdminFacade();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		try {
			facade = (AdminFacade) facade.login(admin.getName(), admin.getPassword());
			session = request.getSession(true);
			session.setAttribute("facade", facade);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			System.out.println("im here");
			throw new CouponSystemWebException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}

	}
	@POST
	@Path("createCustomer")
	public Response createCustomer(Customer customer) throws Exception, CouponSystemException {

		try {
			HttpSession session = request.getSession(false);
			AdminFacade adminFacade = new AdminFacade();
			adminFacade = (AdminFacade) session.getAttribute("facade");
			adminFacade.createCustomer(customer);
			return Response.status(Status.CREATED).build();
		} catch (Exception e) {
			throw new CouponSystemWebException(e.getMessage(), Status.INTERNAL_SERVER_ERROR);
		}
	}

	@DELETE
	@Path("removeCustomer/{id}")
	public Response removeCustomer(@PathParam("id") long id) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		adminFacade.removeCustomer(adminFacade.getCustomer(id));
		return Response.status(Status.NO_CONTENT).build();

	}

	@PUT
	@Path("updateCustomer")
	public Response updateCustomer(Customer customer) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
//		adminFacade = (AdminFacade) session.getAttribute("facade");
		adminFacade.updateCustomer(customer);
		return Response.status(Status.OK).build();
	}

	@GET
	@Path("getCustomer/{id}")
    @Produces("text/html")
	public Response getCustomer(@PathParam("id") long id) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		Customer cust = adminFacade.getCustomer(id);
		Map<String, Customer> model = new HashMap<>();
		model.put("cust", cust);
		return Response.ok(new Viewable("/showCustomer", model)).build();

	}

	@GET
	@Path("getCustomers")
	public Response getCustomers() throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		Collection<Customer> cust = adminFacade.getAllCustomers();
		Map<String, Collection<Customer>> model = new HashMap<>();
		model.put("cust", cust);
		return Response.ok(new Viewable("/showCustomer", model)).build();

	}

	@POST
	@Path("createCompany")
	public Response createCompany(Company company) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		adminFacade.createCompany(company);
		return Response.status(Status.CREATED).build();

	}

	@DELETE
	@Path("removeCompany")
	public Response removeCompany(Company company) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		adminFacade.removeCompany(company);
		return Response.status(Status.NO_CONTENT).build();

	}

	@PUT
	@Path("updateCompany")
	public Response updateCompany(Company company) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		adminFacade.updateCompany(company);
		return Response.status(Status.OK).build();

	}

	@GET
	@Path("getCompany/{id}")
	public Response getCompany(@PathParam("id") long id) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		Company comp = adminFacade.getCompany(id);
		return Response.status(Status.OK).build();

	}

	@GET
	@Path("getCompanies")
	public Response getCompanies() throws Exception, CouponSystemException {

		HttpSession session = request.getSession(true);
		AdminFacade adminFacade = new AdminFacade();
		adminFacade = (AdminFacade) session.getAttribute("facade");
		Collection<Company> comp = adminFacade.getAllCompanies();
		return Response.status(Status.OK).build();

	}
}
