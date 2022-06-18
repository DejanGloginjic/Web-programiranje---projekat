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

import beans.Membership;
import dao.MembershipDAO;
import dao.StartingProject;

@Path("/memberships")
public class MembershipService {
	
	@Context
	ServletContext ctx;
	
	public MembershipService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("membershipDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	StartingProject.getInstance(contextPath);
			ctx.setAttribute("membershipDAO", MembershipDAO.getInstance());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Membership> getMemberships() {
		MembershipDAO dao = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Membership newMembership(Membership membership) {
		MembershipDAO dao = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return dao.save(membership);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Membership findOne(@PathParam("id") int id) {
		MembershipDAO dao = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return dao.find(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Membership search(@QueryParam("name") String name) {
		MembershipDAO dao = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return dao.findAll().stream()
//				.filter(membership -> membership.getTrainingName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Membership changeOne(Membership membership, @PathParam("id") int id) {
		MembershipDAO dao = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return dao.change(membership);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Membership deleteMembership(@PathParam("id") int id) {
		MembershipDAO dao = (MembershipDAO) ctx.getAttribute("membershipDAO");
		return dao.delete(id);
	}

}
