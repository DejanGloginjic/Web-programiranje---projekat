package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Response;

import org.apache.catalina.User;

import beans.Training;
import beans.TrainingHistory;
import beans.Enums.MembershipStatusEnum;
import dao.StartingProject;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
import dao.UserDAO;
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
		// Ovaj objekat se instancira više puta u toku rada aplikacije
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
	@Path("/getTrainingsForCoach/{coachId}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTrainingsForCoach(@PathParam("coachId") int coachId) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		return dao.getTrainingsForCoach(coachId);
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
	
	@POST
	@Path("/addTrainingToUser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addTrainingToUser(TrainingDTO object, @Context HttpServletRequest request) {
		Training objectfound = TrainingDAO.getInstance().find(object.getId());
		beans.User logged = (beans.User) request.getSession().getAttribute("user");
		if(logged == null) {
			return Response.status(400).entity("invalid ").build();
		}
		/*if(logged.getMembership() == null) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}*/
		if(logged.getMembership().getMembershipStatus() == MembershipStatusEnum.Inactive) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		if(logged.getMembership().getNumberOfAppointment() < 1) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		int ostalih = logged.getMembership().getNumberOfAppointment();
		logged.getMembership().setNumberOfAppointment(ostalih - 1);
		
		TrainingHistory history = new TrainingHistory(0, LocalDateTime.now(), objectfound, logged, objectfound.getCoach());
		history = TrainingHistoryDAO.getInstace().save(history);
		if(objectfound.getCoach() != null) {
			objectfound.getCoach().getTrainingHistory().add(history);
		}
		logged.getVisitedObject().add(objectfound.getSportObject());
		
		return Response.status(200).build();
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
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
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
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		ArrayList<Training> groupTrainings = dao.getGroupTrainingsForCoach(idKorisnika);
		ArrayList<TrainingDTO> groupTrainingsDTO = new ArrayList<TrainingDTO>();
		for(Training t : groupTrainings) {
			groupTrainingsDTO.add(new TrainingDTO(t));
		}
		return groupTrainingsDTO;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void changeOne(TrainingDTO trainingDTO) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		Training training = new Training();
		training.setId(trainingDTO.getId());
		training.setTrainingName(trainingDTO.getTrainingName());
		training.setTrainingType(trainingDTO.getTrainingType());
		training.setSportObject(trainingDTO.getSportObject());
		training.setDuration(trainingDTO.getDuration());
		training.setDescription(trainingDTO.getDescription());
		training.setImage(trainingDTO.getImage());
		training.setCoach(userDAO.find(trainingDTO.getCoachId()));
		
		dao.change(training);
	}
	
	@POST
	@Path("/setSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setSelected(TrainingDTO object, @Context HttpServletRequest request) {
		Training objectfound = TrainingDAO.getInstance().find(object.getId());
		request.getSession().setAttribute("selectedTraining", objectfound);
		return Response.status(200).build();
	}
	
	@GET
	@Path("/getSelected")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TrainingDTO getSelected( @Context HttpServletRequest request) {
		Training object = (Training)request.getSession().getAttribute("selectedTraining");
		return new TrainingDTO(object);
	}
	
	
	
}
