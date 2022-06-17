package beans;

import beans.Enums.TrainingTypeEnum;

public class Training {
	
	private int id;
	private String trainingName;
	private TrainingTypeEnum trainingType;
	private SportObject sportObject;
	private int duration;
	private User coach;
	private String description;
	private String image;
	
	public Training(int id, String trainingName, TrainingTypeEnum trainingType, SportObject sportObject, int duration,
			User coach, String description, String image) {
		super();
		this.id = id;
		this.trainingName = trainingName;
		this.trainingType = trainingType;
		this.sportObject = sportObject;
		this.duration = duration;
		this.coach = coach;
		this.description = description;
		this.image = image;
	}

	public Training(int id) {
		this.id = id;
	}
<<<<<<< HEAD
=======
	
	public Training() {
		super();
	}
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb

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
<<<<<<< HEAD

	public void setSportObject(SportObject sportObject) {
		this.sportObject = sportObject;
	}

=======

	public void setSportObject(SportObject sportObject) {
		this.sportObject = sportObject;
	}

>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
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
