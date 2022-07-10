package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Comment;
import beans.PromoCode;
import beans.ScheduledTraining;
import dao.CommentDAO;
import dao.PromoCodeDAO;
import dao.ScheduledTrainingDAO;
import dao.StartingProject;
import dao.TrainingHistoryDAO;
import dto.TrainingDTO;

@Path("/promoCodes")
public class PromoCodeService {
	
	@Context
	ServletContext ctx;
	
	public PromoCodeService() {
	}

	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("promoCodeDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	StartingProject.getInstance(contextPath);
			ctx.setAttribute("promoCodeDAO", PromoCodeDAO.getInstace());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PromoCode> getPromoCodes() {
		PromoCodeDAO dao = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/getByCode")
	@Produces(MediaType.APPLICATION_JSON)
	public PromoCode getByCode(String code) {
		PromoCodeDAO dao = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
		return dao.getByCode(code);
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PromoCode newPromoCode(PromoCode code) {
		PromoCodeDAO dao = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
		return dao.save(code);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PromoCode findOne(@PathParam("id") int id) {
		PromoCodeDAO dao = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
		return dao.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PromoCode changeOne(PromoCode code, @PathParam("id") int id) {
		PromoCodeDAO dao = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
		return dao.change(code);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PromoCode deletePromoCode(@PathParam("id") int id) {
		PromoCodeDAO dao = (PromoCodeDAO) ctx.getAttribute("promoCodeDAO");
		return dao.delete(id);
	}
	
}
