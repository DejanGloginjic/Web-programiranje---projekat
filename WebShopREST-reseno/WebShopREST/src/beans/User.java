package beans;

import java.time.LocalDate;
import java.util.ArrayList;

import beans.Enums.UserGenderEnum;
import beans.Enums.UserTypeEnum;

public class User {

	private String username;
	private String password;
	private String name;
	private String surname;
	private UserGenderEnum userGender;
	private LocalDate dateOfBirth;
	private UserTypeEnum userType;
	private ArrayList<TrainingHistory> trainingHistory;
	private Membership membership;
	private SportObject sportObject;
	private ArrayList<SportObject> visitedObject;
	private int points;
	private BuyerType buyerType;
	
	
}
