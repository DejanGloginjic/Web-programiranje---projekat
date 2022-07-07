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

import beans.Training;
import dao.StartingProject;
import dao.TrainingDAO;
import dto.TrainingDTO;

@Path("/trainings")
public class TrainingService {
	
	@Context
	ServletContext ctx;
	
	public TrainingService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("trainingDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
	    	StartingProject.getInstance(contextPath);
			ctx.setAttribute("trainingDAO", TrainingDAO.getInstance());
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Training> getTrainings() {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.findAll();
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Training newTraining(Training training) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.save(training);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Training findOne(@PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.find(id);
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Training search(@QueryParam("name") String name) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.findAll().stream()
				.filter(training -> training.getTrainingName().equals(name))
				.findFirst()
				.orElse(null);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Training changeOne(Training training, @PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.change(training);
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Training deleteTraining(@PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.delete(id);
	}
	
	@GET
	@Path("/getTrainingsForObject/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<TrainingDTO> getTrainingsForObject(@PathParam("id") int id) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		ArrayList<Training> foundTrainings = dao.getInstance().getTrainingForSportObject(id);
		ArrayList<TrainingDTO> trainingsDTO = new ArrayList<TrainingDTO>();
		for(Training training : foundTrainings) {
			trainingsDTO.add(new TrainingDTO(training));
		}
		return trainingsDTO;
	}
	
	@GET
	@Path("/getPersonalTrainings")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TrainingDTO> getPersonalniTreninziZaTrenera(@QueryParam("idKorisnika") int idKorisnika) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("treningDAO");
		ArrayList<Training> personalTrainings = dao.getPesonalTrainingsForCoach(idKorisnika);
		ArrayList<TrainingDTO> personalTrainingsDTO = new ArrayList<TrainingDTO>();
		for(Training t : personalTrainings) {
			personalTrainingsDTO.add(new TrainingDTO(t));
		}
		return personalTrainingsDTO;
	}
	
	@GET
	@Path("/getGroupTrainings")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TrainingDTO> getGrupniTreninziZaTrenera(@QueryParam("idKorisnika") int idKorisnika) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("treningDAO");
		ArrayList<Training> groupTrainings = dao.getGroupTrainingsForCoach(idKorisnika);
		ArrayList<TrainingDTO> groupTrainingsDTO = new ArrayList<TrainingDTO>();
		for(Training t : groupTrainings) {
			groupTrainingsDTO.add(new TrainingDTO(t));
		}
		return groupTrainingsDTO;
	}
	
}
