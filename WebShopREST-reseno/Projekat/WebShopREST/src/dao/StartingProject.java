package dao;

public class StartingProject {

	private static StartingProject instance = null;
	
	private StartingProject(String contextPath) {
		UserDAO.getInstance(contextPath).loadUsers(contextPath);
		BuyerTypeDAO.getInstance(contextPath).loadBuyers(contextPath);
		CommentDAO.getInstace(contextPath).loadComments(contextPath);
		LocationDAO.getInstace(contextPath).loadLocations(contextPath);
		MembershipDAO.getInstance(contextPath).loadMemberships(contextPath);
		SportObjectDAO.getInstance(contextPath).loadSportObjects(contextPath);
		TrainingDAO.getInstance(contextPath).loadTrainings(contextPath);
		TrainingHistoryDAO.getInstace(contextPath).loadTrainingHistory(contextPath);
		
		UserDAO.getInstance(contextPath).linkUserAndMembership(contextPath);
		UserDAO.getInstance(contextPath).linkUserAndSportObject(contextPath);
		UserDAO.getInstance(contextPath).linkUserAndBuyerType(contextPath);
		
		CommentDAO.getInstace(contextPath).linkCommentAndSportObject(contextPath);
		CommentDAO.getInstace(contextPath).linkCommentAndUser(contextPath);
		
		SportObjectDAO.getInstance(contextPath).linkSportObjectAndLocation(contextPath);
		
		TrainingDAO.getInstance(contextPath).linkTrainingAndCoach(contextPath);
		TrainingDAO.getInstance(contextPath).linkTrainingAndSportObject(contextPath);
		
		TrainingHistoryDAO.getInstace(contextPath).linkTrainingHistoryAndBuyer(contextPath);
		TrainingHistoryDAO.getInstace(contextPath).linkTrainingHistoryAndCoach(contextPath);
		TrainingHistoryDAO.getInstace(contextPath).linkTrainingHistoryAndTraining(contextPath);
	}
	
	public static StartingProject getInstance(String contextPath) {
		if(instance == null) {
			instance = new StartingProject(contextPath);
		}
		return instance;
	}
}
