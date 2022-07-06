package dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import beans.BuyerType;
import beans.Membership;
import beans.SportObject;
import beans.User;
import beans.Enums.DateHelper;
import beans.Enums.UserGenderEnum;
import beans.Enums.UserTypeEnum;


public class UserDTO {
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private UserGenderEnum userGender;
	private LocalDate dateOfBirth;
	private UserTypeEnum userType;

	//buyer
	private Membership membership;
	private ArrayList<SportObject> visitedObject;
	private int points;
	private BuyerType buyerType;
	
	//menager
	private SportObject sportObject;

	public UserDTO() {
		super();
		this.visitedObject = new ArrayList<SportObject>();
	}

	public UserDTO(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.userGender = user.getUserGender();
		this.dateOfBirth = DateHelper.stringToDate(user.getDateOfBirth());
		this.userType = user.getUserType();
		this.membership = user.getMembership();
		this.visitedObject = user.getVisitedObject();
		this.points = user.getPoints();
		this.buyerType = user.getBuyerType();
		this.sportObject = user.getSportObject();
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

	public String getDateOfBirth() {
		return DateHelper.dateToString(dateOfBirth);
	}

	public void setDateOfBirth1(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = DateHelper.stringToDate(dateOfBirth);
	}

	public UserTypeEnum getUserType() {
		return userType;
	}

	public void setUserType(UserTypeEnum userType) {
		this.userType = userType;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
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

	public SportObject getSportObject() {
		return sportObject;
	}

	public void setSportObject(SportObject sportObject) {
		this.sportObject = sportObject;
	}
}