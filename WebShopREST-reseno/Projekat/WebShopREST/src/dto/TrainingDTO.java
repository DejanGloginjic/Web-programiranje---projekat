package dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import beans.ScheduledTraining;
import beans.SportObject;
import beans.Training;
import beans.Enums.ScheduledTrainingStatus;
import beans.Enums.TrainingTypeEnum;

public class TrainingDTO {
	private int id;
	private String trainingName;
	private TrainingTypeEnum trainingType;
	private SportObject sportObject;
	private int duration;
	private String description;
	private String image;
	private String trainerName;
	private String buyerName;
	private LocalDateTime trainingDateAndTime;
	private LocalDate dateOfApplication;
	private ScheduledTrainingStatus status;
	private int coachId;
	
	public TrainingDTO(Training training) {
		this.id = training.getId();
		this.trainingName = training.getTrainingName();
		this.trainingType = training.getTrainingType();
		this.sportObject = training.getSportObject();
		this.duration = training.getDuration();
		this.description = training.getDescription();
		this.image = training.getImage();
		this.trainerName = (training.getCoach()==null)?null:(training.getCoach().getName() + " " + training.getCoach().getSurname());
		this.coachId = training.getCoach().getId();
	}
	
	public TrainingDTO(ScheduledTraining training) {
		this.id = training.getId();
		this.trainingName = training.getTraining().getTrainingName();
		this.trainingType = training.getTraining().getTrainingType();
		this.sportObject = training.getTraining().getSportObject();
		this.duration = training.getTraining().getDuration();
		this.description = training.getTraining().getDescription();
		this.image = training.getTraining().getImage();
		this.trainerName = (training.getTraining().getCoach()==null)?null:(training.getTraining().getCoach().getName() + " " + training.getTraining().getCoach().getSurname());
		this.buyerName = (training.getBuyer()==null)?null:(training.getBuyer().getName() + " " + training.getBuyer().getSurname());
		this.trainingDateAndTime = training.getTrainingDateAndTime();
		this.dateOfApplication = training.getDateOfApplication();
		this.status = training.getStatus();
	}
	
	public TrainingDTO(int id, String trainingName, TrainingTypeEnum trainingType, SportObject sportObject,
			int duration, String description, String image) {
		super();
		this.id = id;
		this.trainingName = trainingName;
		this.trainingType = trainingType;
		this.sportObject = sportObject;
		this.duration = duration;
		this.description = description;
		this.image = image;
	}

	public ScheduledTrainingStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduledTrainingStatus status) {
		this.status = status;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public LocalDateTime getTrainingDateAndTime() {
		return trainingDateAndTime;
	}

	public void setTrainingDateAndTime(LocalDateTime trainingDateAndTime) {
		this.trainingDateAndTime = trainingDateAndTime;
	}

	public LocalDate getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(LocalDate dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public TrainingDTO(int id) {
		this.id = id;
	}

	public TrainingDTO() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public TrainingTypeEnum getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingTypeEnum trainingType) {
		this.trainingType = trainingType;
	}

	public SportObject getSportObject() {
		return sportObject;
	}

	public void setSportObject(SportObject sportObject) {
		this.sportObject = sportObject;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public String getTrainerName() {
		return trainerName;
	}



	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}
	
	public int getCoachId() {
		return coachId;
	}

	public void setCoachID(int coachId) {
		this.coachId = coachId;
	}
	
	

}
