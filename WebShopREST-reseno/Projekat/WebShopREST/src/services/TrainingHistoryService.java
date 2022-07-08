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

import beans.TrainingHistory;
import dao.StartingProject;
import dao.TrainingHistoryDAO;
import dto.TrainingHistoryDTO;

@Path("/trainingHistory")
public class TrainingHistoryService {
	
	@Context
	ServletContext ctx;
	
	public TrainingHistoryService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira više puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	StartingProject.getInstance(contextPath);
			ctx.setAttribute("trainingHistoryDAO", TrainingHistoryDAO.getInstace());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TrainingHistory> getTrainingHistories() {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TrainingHistory newTrainingHistory(TrainingHistory trainingHistory) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.save(trainingHistory);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TrainingHistory findOne(@PathParam("id") int id) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.find(id);
	}
	 /*
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Training search(@QueryParam("name") String name) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.findAll().stream()
				.filter(training -> training.getTrainingName().equals(name))
				.findFirst()
				.orElse(null);
	}*/
	
	@GET
	@Path("/getITforUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TrainingHistoryDTO> getTrainingHistoryforUser(@QueryParam("idKorisnika") int idKorisnika) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		ArrayList<TrainingHistory> treninzi = (ArrayList<TrainingHistory>) dao.getTrainingHistoryforUser(idKorisnika);
		ArrayList<TrainingHistoryDTO> treninziDTO = new ArrayList<TrainingHistoryDTO>();
		for(TrainingHistory istorija : treninzi) {
			treninziDTO.add(new TrainingHistoryDTO(istorija));
		}
		return treninziDTO;
	} 
	
	
	@GET
	@Path("/getIsVisited")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean getTrainingHistoryforSportObjectAndUser(@QueryParam("objectId") int objectId, @QueryParam("userId") int userId) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		boolean isVisited = dao.getTrainingHistoryforSportObjectAndUser(objectId, userId);
		return isVisited;
	} 
	
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TrainingHistory changeOne(TrainingHistory training, @PathParam("id") int id) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.change(training);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TrainingHistory deleteTrainingHistory(@PathParam("id") int id) {
		TrainingHistoryDAO dao = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		return dao.delete(id);
	}
	
}
