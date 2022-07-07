package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Response;

import beans.SportObject;
import dao.SportObjectDAO;
import dao.StartingProject;


@Path("/sportobjects")
public class SportObjectService {
	
	@Context
	ServletContext ctx;
	
	public SportObjectService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("sportObjectDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	StartingProject.getInstance(contextPath);
			ctx.setAttribute("sportObjectDAO", SportObjectDAO.getInstance());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<SportObject> getSportObjects() {
		SportObjectDAO dao = (SportObjectDAO) ctx.getAttribute("sportObjectDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SportObject newSportObject(SportObject sportobject) {
		SportObjectDAO dao = (SportObjectDAO) ctx.getAttribute("sportObjectDAO");
		return dao.save(sportobject);
	}
	
	@POST
	@Path("/calculateGrade")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public double calculateGrade(int grade, SportObject sportobject) {
		SportObjectDAO dao = (SportObjectDAO) ctx.getAttribute("sportObjectDAO");
		return dao.calculateGrade(grade, sportobject);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportObject findOne(@PathParam("id") int id) {
		SportObjectDAO dao = (SportObjectDAO) ctx.getAttribute("sportObjectDAO");
		return dao.find(id);
	}
	
	@POST
	@Path("/setSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setSelected(SportObject object, @Context HttpServletRequest request) {
		object = SportObjectDAO.getInstance().find(object.getId());
		request.getSession().setAttribute("selected", object);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/getSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SportObject getSelected( @Context HttpServletRequest request) {
		SportObject object = (SportObject)request.getSession().getAttribute("selected");
		return object;
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SportObject> search(@QueryParam("searchValue") String searchValue, @QueryParam("criterion") String criterion) {
		SportObjectDAO dao = (SportObjectDAO) ctx.getAttribute("sportObjectDAO");
		return dao.search(searchValue, criterion);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SportObject changeOne(SportObject sportobject, @PathParam("id") int id) {
		SportObjectDAO dao = (SportObjectDAO) ctx.getAttribute("sportObjectDAO");
		return dao.change(sportobject);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportObject deleteSportObject(@PathParam("id") int id) {
		SportObjectDAO dao = (SportObjectDAO) ctx.getAttribute("sportObjectDAO");
		return dao.delete(id);
	}

}
