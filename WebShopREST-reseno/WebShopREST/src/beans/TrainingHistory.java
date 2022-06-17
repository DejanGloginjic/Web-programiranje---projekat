package beans;

import java.time.LocalDateTime;

public class TrainingHistory {
	
	private int id;
	private LocalDateTime treningEntrance;
	private Training training;
	private User buyer;
	private User coach;
	
	public TrainingHistory(int id, LocalDateTime treningEntrance, Training training, User buyer, User coach) {
		super();
		this.id = id;
		this.treningEntrance = treningEntrance;
		this.training = training;
		this.buyer = buyer;
		this.coach = coach;
	}

	public TrainingHistory() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getTreningEntrance() {
		return treningEntrance;
	}

	public void setTreningEntrance(LocalDateTime treningEntrance) {
		this.treningEntrance = treningEntrance;
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

	public User getCoach() {
		return coach;
	}

	public void setCoach(User coach) {
		this.coach = coach;
	}

	
}
