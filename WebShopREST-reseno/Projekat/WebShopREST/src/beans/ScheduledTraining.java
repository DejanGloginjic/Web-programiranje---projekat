package beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

import beans.Enums.DateHelper;
import beans.Enums.LocalDateTimeHelper;
import beans.Enums.ScheduledTrainingStatus;

public class ScheduledTraining {

	private int id;
	private Training training;
	private User buyer;
	private LocalDateTime trainingDateAndTime;
	private LocalDate dateOfApplication;
	private ScheduledTrainingStatus status;
	
	public ScheduledTraining(int id, Training training, User buyer, LocalDateTime trainingDateAndTime,
			LocalDate dateOfApplication, ScheduledTrainingStatus status) {
		super();
		this.id = id;
		this.training = training;
		this.buyer = buyer;
		this.trainingDateAndTime = trainingDateAndTime;
		this.dateOfApplication = dateOfApplication;
		this.status = status;
	}

	public ScheduledTraining() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScheduledTrainingStatus getStatus() {
		return status;
	}

	public void setStatus(ScheduledTrainingStatus status) {
		this.status = status;
	}

	public ScheduledTraining(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
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
	
	public String fileLine() {
		return  id + ";" + training.getId() + ";" + buyer.getId() + ";" + LocalDateTimeHelper.dateToString(trainingDateAndTime) 
		+ ";" + DateHelper.dateToString(dateOfApplication) + ";" + status.ordinal();
	}
	
}
