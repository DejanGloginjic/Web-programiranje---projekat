package services;

import java.util.ArrayList;
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

import beans.SportObject;
import beans.User;
import dao.StartingProject;
import dao.UserDAO;
import dto.UserDTO;



@Path("/users")
public class UserService {
	@Context
	ServletContext ctx;
	
	public UserService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("userDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	StartingProject.getInstance(contextPath);
			ctx.setAttribute("userDAO", UserDAO.getInstance());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserDTO> getUsers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		Collection<User> users = dao.findAll();
		ArrayList<UserDTO> usersDTO = new ArrayList<UserDTO>();
		for(User u: users) {
			usersDTO.add(new UserDTO(u));
		}
		return usersDTO;
	}

	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User newUser(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.save(user);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User findOne(@PathParam("id") int id) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.find(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public User search(@QueryParam("name") String name) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.findAll().stream()
				.filter(user -> user.getName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	@GET
	@Path("/freeManagers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getFreeManagers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.getAllFreeManagers();
	}
	
	@GET
	@Path("/getTrainers/{objectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getTrainersForSportObject(@PathParam("objectId") int objectId){
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.getTrainersForSportObject(objectId);
	}
	
	@GET
	@Path("/getBuyers/{objectId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getBuyersForSportObject(@PathParam("objectId") int objectId){
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.getBuyersForSportObject(objectId);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User changeOne(User user) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.change(user);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User deleteUser(@PathParam("id") int id) {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		return dao.delete(id);
	}
	
	@GET
	@Path("/trainers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UserDTO> getTrainers() {
		UserDAO dao = (UserDAO) ctx.getAttribute("userDAO");
		ArrayList<User> coaches = dao.getTCoaches();
		ArrayList<UserDTO> coachDTOs = new ArrayList<UserDTO>();
		
		for(User user : coaches) {
			coachDTOs.add(new UserDTO(user));
		}
		
		return coachDTOs;
	}
	
	@GET
	@Path("/getBuyersForObject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<UserDTO> getPeopleThatVisitedObject(@PathParam("id") int id) {
		
		ArrayList<User> usersFound = UserDAO.getInstance().getBuyersForSportObject(id);
		ArrayList<UserDTO> usersDTO = new ArrayList<UserDTO>();
		
		for(User user : usersFound) {
			usersDTO.add(new UserDTO(user));
		}
		return usersDTO;
	}
	
	@GET
	@Path("/getTrainersForObject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<UserDTO> getTrainersForObject(@PathParam("id") int id) {
		
		ArrayList<User> usersFound = UserDAO.getInstance().getTrainersForSportObject(id);
		ArrayList<UserDTO> usersDTO = new ArrayList<UserDTO>();
		
		for(User user : usersFound) {
			usersDTO.add(new UserDTO(user));
		}
		return usersDTO;
	}

} 
