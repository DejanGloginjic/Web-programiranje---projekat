package dto;

import beans.SportObject;
import beans.Training;
import beans.Enums.TrainingTypeEnum;

public class TrainingDTO {
	private int id;
	private String trainingName;
	private TrainingTypeEnum trainingType;
	private SportObject sportObject;
	private int duration;
	private String description;
	private String image;
	
	public TrainingDTO(Training training) {
		this.id = training.getId();
		this.trainingName = training.getTrainingName();
		this.trainingType = training.getTrainingType();
		this.sportObject = training.getSportObject();
		this.duration = training.getDuration();
		this.description = training.getDescription();
		this.image = training.getImage();
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

}
