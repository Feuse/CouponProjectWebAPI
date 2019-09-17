package services;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.client.ClientResponse.Status;

import beans.Coupon;
import beans.Customer;
import exceptions.CouponSystemException;
import facades.CustomerFacade;

@Path("/customer")
public class CustomerService {

	@Context
	HttpServletRequest request;
	HttpServletResponse response;

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(Customer customer) throws CouponSystemWebException, CouponSystemException {
		CustomerFacade facade = new CustomerFacade();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		try {
			facade = (CustomerFacade) facade.login(customer.getCustName(), customer.getPassword());
			session = request.getSession(true);
			session.setAttribute("facade", facade);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			throw new CouponSystemWebException(e.getMessage(), Status.UNAUTHORIZED);
		}
	}

	@POST
	@Path("purchaseCoupon")
	public Response purchaseCoupon(Coupon coupon) throws Exception, CouponSystemException {

		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = new CustomerFacade();
		customerFacade = (CustomerFacade) session.getAttribute("facade");
		customerFacade.PurchaseCoupon(coupon);
		return Response.ok("Coupon" + coupon.toString() + " was purchased!").build();

	}

	@GET
	@Path("getCoupons")
	public Response getCoupons() throws CouponSystemException {

		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = new CustomerFacade();
		customerFacade = (CustomerFacade) session.getAttribute("facade");
		customerFacade.getAllPurchasedCoupons();
		return Response.ok("Coupons retrived!").build();

	}
}
