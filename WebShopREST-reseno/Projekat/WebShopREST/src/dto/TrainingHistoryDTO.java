package dto;

import java.time.LocalDateTime;

import beans.Training;
import beans.TrainingHistory;
import beans.User;

public class TrainingHistoryDTO {

		
		private int id;
		private LocalDateTime treningEntrance;
		private TrainingDTO training;
		private User coach;
		
		public TrainingHistoryDTO() {
			
		}
		
		public TrainingHistoryDTO(TrainingHistory th) {
			this.id = th.getId();
			this.treningEntrance = th.getTreningEntrance();
			this.training = new TrainingDTO(th.getTraining());
			this.coach = th.getCoach();
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

		public TrainingDTO getTraining() {
			return training;
		}

		public void setTraining(TrainingDTO training) {
			this.training = training;
		}

		

		public int getCoach() {
			return (coach==null)?-1:getIntId();
		}

		public void setCoach(User coach) {
			this.coach = coach;
		}
		
		
		
		
		
		
		
}

