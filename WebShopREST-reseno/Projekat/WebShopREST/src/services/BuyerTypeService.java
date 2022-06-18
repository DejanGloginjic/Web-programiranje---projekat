package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.BuyerType;
import dao.BuyerTypeDAO;


@Path("/buyerType")
public class BuyerTypeService {
	@Context
	ServletContext ctx;
	
	public BuyerTypeService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("buyerTypeDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("buyerTypeDAO", BuyerTypeDAO.getInstance(contextPath));
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<BuyerType> getBuyerTypes() {
		BuyerTypeDAO dao = (BuyerTypeDAO) ctx.getAttribute("buyerTypeDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public BuyerType newBuyerType(BuyerType buyertype) {
		BuyerTypeDAO dao = (BuyerTypeDAO) ctx.getAttribute("buyerTypeDAO");
		return dao.save(buyertype);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType findOne(@PathParam("id") int id) {
		BuyerTypeDAO dao = (BuyerTypeDAO) ctx.getAttribute("buyerTypeDAO");
		return dao.find(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType search(@QueryParam("name") String name) {
		BuyerTypeDAO dao = (BuyerTypeDAO) ctx.getAttribute("buyerTypeDAO");
		return dao.findAll().stream()
//				.filter(buyertype -> buyertype.getBuyerTypeName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType changeOne(BuyerType buyertype, @PathParam("id") int id) {
		BuyerTypeDAO dao = (BuyerTypeDAO) ctx.getAttribute("buyerTypeDAO");
		return dao.change(buyertype);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public BuyerType deleteBuyerType(@PathParam("id") int id) {
		BuyerTypeDAO dao = (BuyerTypeDAO) ctx.getAttribute("buyerTypeDAO");
		return dao.delete(id);
	}

}
