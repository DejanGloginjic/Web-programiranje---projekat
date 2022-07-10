package dao;

public class StartingProject {

	private static StartingProject instance = null;
	
	private StartingProject(String contextPath) {
		UserDAO.getInstance().loadUsers(contextPath);
		BuyerTypeDAO.getInstance().loadBuyers(contextPath);
		CommentDAO.getInstace().loadComments(contextPath);
		LocationDAO.getInstace().loadLocations(contextPath);
		MembershipDAO.getInstance().loadMemberships(contextPath);
		SportObjectDAO.getInstance().loadSportObjects(contextPath);
		TrainingDAO.getInstance().loadTrainings(contextPath);
		TrainingHistoryDAO.getInstace().loadTrainingHistory(contextPath);
		ScheduledTrainingDAO.getInstance().loadTrainings(contextPath);
		PromoCodeDAO.getInstace().loadPromoCodes(contextPath);

		
		UserDAO.getInstance().linkUserAndMembership();
		UserDAO.getInstance().linkUserAndSportObject();
		UserDAO.getInstance().linkUserAndBuyerType();
		UserDAO.getInstance().linkUserAndVisitedObject(contextPath);
		
		CommentDAO.getInstace().linkCommentAndSportObject();
		CommentDAO.getInstace().linkCommentAndUser();
		
		SportObjectDAO.getInstance().linkSportObjectAndLocation();
		
		TrainingDAO.getInstance().linkTrainingAndCoach();
		TrainingDAO.getInstance().linkTrainingAndSportObject();
		
		TrainingHistoryDAO.getInstace().linkTrainingHistoryAndBuyer();
		TrainingHistoryDAO.getInstace().linkTrainingHistoryAndCoach();
		TrainingHistoryDAO.getInstace().linkTrainingHistoryAndTraining();
		
		ScheduledTrainingDAO.getInstance().linkScheduledTrainingAndBuyer();
		ScheduledTrainingDAO.getInstance().linkScheduledTrainingAndTraining();
	}
	
	public static StartingProject getInstance(String contextPath) {
		if(instance == null) {
			instance = new StartingProject(contextPath);
		}
		return instance;
	}
}
