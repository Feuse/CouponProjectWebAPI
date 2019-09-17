package services;

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
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.view.Viewable;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;
import facades.CompanyFacade;

@Path("company")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@XmlRootElement
public class CompanyService {

	@Context
	HttpServletRequest request;
	HttpServletResponse response;
	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("p")
	public Response index() {
		return Response.ok(new Viewable("/index.jsp")).build();
	}
	
	@POST
	@Path("login")
	public Response login(Company company) throws CouponSystemWebException, CouponSystemException {
		System.out.println("inside comp login");
		CompanyFacade facade = new CompanyFacade();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		try {
			facade = (CompanyFacade) facade.login(company.getCompName(), company.getPassword());
			session = request.getSession(true);
			session.setAttribute("facade", facade);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			throw new CouponSystemWebException(e.getMessage(), Status.UNAUTHORIZED);
		}
	}
	
	@POST
	@Path("/test")
	public void test(Company company) {
		System.out.println("aaaaa");
	}
	

	@POST
	@Path("createCoupon")
	public Response createCoupon(Coupon coupon) throws  CouponSystemException {
		 HttpSession session = request.getSession(false);
		 CompanyFacade facade = new CompanyFacade();
		 facade = (CompanyFacade) session.getAttribute("facade");
		try {
			facade.createCoupon(coupon);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			throw new CouponSystemException("CompanyService: " + e.getMessage());
		}
	}
	
	@GET
	@Path("getCoupon/{couponId}")
	public Response getCoupon(@PathParam("couponId")long couponId) throws  CouponSystemException {
		 HttpSession session = request.getSession(false);
		 CompanyFacade facade = new CompanyFacade();
		 facade = (CompanyFacade) session.getAttribute("facade");
		try {
			facade.getCouponById(couponId);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			throw new CouponSystemException("CompanyService: " + e.getMessage());
		}
	}
	
	
	
	@DELETE
	@Path("removeCoupon/{id}")
	public Response removeCoupon(@PathParam("id") long id)  throws CouponSystemException {
		 HttpSession session = request.getSession(false);		
		 CompanyFacade facade = new CompanyFacade();
		 facade = (CompanyFacade) session.getAttribute("facade");
		try {
			Coupon coupon = facade.getCouponById(id);
			facade.removeCoupon(coupon);
			return Response.status(Status.CREATED).build();
		} catch (Exception e) {
			throw new CouponSystemException("CompanyService: " + e.getMessage());
		}
}
	
	@PUT
	@Path("updateCoupon")
	public Response updateCoupon(Coupon coupon) throws CouponSystemException {
		 HttpSession session = request.getSession(false);		
		 CompanyFacade facade = new CompanyFacade();
		 facade = (CompanyFacade) session.getAttribute("facade");
		 try {
			facade.updateCoupon(coupon);
			return Response.status(Status.CREATED).build();
		 } catch (CouponSystemException e) {
				throw new CouponSystemException("CompanyService: " + e.getMessage());
			}
	}
}
