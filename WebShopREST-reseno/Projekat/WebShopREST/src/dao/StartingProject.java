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
		
		UserDAO.getInstance().linkUserAndMembership(contextPath);
		UserDAO.getInstance().linkUserAndSportObject(contextPath);
		UserDAO.getInstance().linkUserAndBuyerType(contextPath);
		UserDAO.getInstance().linkUserAndVisitedObject(contextPath);
		
		CommentDAO.getInstace().linkCommentAndSportObject(contextPath);
		CommentDAO.getInstace().linkCommentAndUser(contextPath);
		
		SportObjectDAO.getInstance().linkSportObjectAndLocation(contextPath);
		
		TrainingDAO.getInstance().linkTrainingAndCoach(contextPath);
		TrainingDAO.getInstance().linkTrainingAndSportObject(contextPath);
		
		TrainingHistoryDAO.getInstace().linkTrainingHistoryAndBuyer(contextPath);
		TrainingHistoryDAO.getInstace().linkTrainingHistoryAndCoach(contextPath);
		TrainingHistoryDAO.getInstace().linkTrainingHistoryAndTraining(contextPath);
	}
	
	public static StartingProject getInstance(String contextPath) {
		if(instance == null) {
			instance = new StartingProject(contextPath);
		}
		return instance;
	}
}
