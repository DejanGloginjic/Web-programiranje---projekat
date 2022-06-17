package beans;

import java.time.LocalDate;
import java.util.ArrayList;

import beans.Enums.UserGenderEnum;
import beans.Enums.UserTypeEnum;

public class User {

	private int id;
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
	
<<<<<<< HEAD
	public User(int id,String username, String password, String name, String surname, UserGenderEnum userGender,
=======
	public User(int id) {
		this.id = id;
	}
	
	public User(String username, String password, String name, String surname, UserGenderEnum userGender,
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
			LocalDate dateOfBirth, UserTypeEnum userType, ArrayList<TrainingHistory> trainingHistory,
			Membership membership, SportObject sportObject, ArrayList<SportObject> visitedObject, int points,
			BuyerType buyerType) {
		super();
<<<<<<< HEAD
		this.id = id;
=======
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.userGender = userGender;
		this.dateOfBirth = dateOfBirth;
		this.userType = userType;
		this.trainingHistory = trainingHistory;
		this.membership = membership;
		this.sportObject = sportObject;
		this.visitedObject = visitedObject;
		this.points = points;
		this.buyerType = buyerType;
	}
<<<<<<< HEAD
	
	public User(int id) {
		super();
		this.id = id;
	}
=======
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public UserGenderEnum getUserGender() {
		return userGender;
	}

	public void setUserGender(UserGenderEnum userGender) {
		this.userGender = userGender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public ArrayList<TrainingHistory> getTrainingHistory() {
		return trainingHistory;
	}

	public void setTrainingHistory(ArrayList<TrainingHistory> trainingHistory) {
		this.trainingHistory = trainingHistory;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public SportObject getSportObject() {
		return sportObject;
	}

	public void setSportObject(SportObject sportObject) {
		this.sportObject = sportObject;
	}

	public ArrayList<SportObject> getVisitedObject() {
		return visitedObject;
	}

	public void setVisitedObject(ArrayList<SportObject> visitedObject) {
		this.visitedObject = visitedObject;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public BuyerType getBuyerType() {
		return buyerType;
	}

	public void setBuyerType(BuyerType buyerType) {
		this.buyerType = buyerType;
	}
	
	
}
