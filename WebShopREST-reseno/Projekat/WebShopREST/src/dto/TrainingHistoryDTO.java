package dto;

import java.time.LocalDateTime;

import beans.Training;
import beans.TrainingHistory;
import beans.User;

public class TrainingHistoryDTO {

		
		private int id;
		private LocalDateTime treningEntrance;
		private Training training;
		private User buyer;
		private User coach;
		
		public TrainingHistoryDTO() {
			
		}
		
		public TrainingHistoryDTO(TrainingHistory th) {
			this.id = th.getId();
			this.treningEntrance = th.getTreningEntrance();
			this.training = th.getTraining();
			this.buyer = th.getBuyer();
			this.coach = th.getCoach();
		}
		
		public TrainingHistoryDTO(int id, LocalDateTime treningEntrance, Training training, User buyer,
				User coach) {
			super();
			this.id = id;
			this.treningEntrance = treningEntrance;
			this.training = training;
			this.buyer = buyer;
			this.coach = coach;
		}
		public int getIntId() {
			return id;
		}
		public void setIntId(int intId) {
			this.id = intId;
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

		public int getCoach() {
			return (coach==null)?-1:getIntId();
		}

		public void setCoach(User coach) {
			this.coach = coach;
		}
		
		
		
		
		
		
		
}

