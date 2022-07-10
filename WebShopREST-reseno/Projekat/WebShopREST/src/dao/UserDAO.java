package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.BuyerType;
import beans.Membership;
import beans.SportObject;
import beans.TrainingHistory;
import beans.User;
import beans.Enums.UserGenderEnum;
import beans.Enums.BuyerTypeEnum;
import beans.Enums.DateHelper;
import beans.Enums.UserGenderEnum;
import beans.Enums.UserTypeEnum;

/*
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class UserDAO {
	
	private static UserDAO userInstance = null;
	private static String contextPath = "";
	
	private Map<Integer, User> users = new HashMap<>();
	
	
	private UserDAO() {
		
	}
	
	/*
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	
	
	public static UserDAO getInstance() {
		if(userInstance == null) {
			userInstance = new UserDAO();
		}
		return userInstance;
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public User check(String username, String password) {
		for(User user : users.values()) {
			if(user.getUsername().equals(username)) {
				if(user.getPassword().equals(password)) {
					return user;
				}
			}
		}
		return null;
	}
	

	public User find(int id) {
		return users.get(id);
	}
	
	public Collection<User> findAll() {
		return users.values();
	}
	
	
	public User save(User user) {
		
		if(user.getUserType() == UserTypeEnum.Buyer) {
			BuyerType type = new BuyerType(0);
			type.setBuyerType(BuyerTypeEnum.Bronze);
			type.setDiscount(0);
			type.setPoints(0);
			type = BuyerTypeDAO.getInstance().save(type);
			user.setBuyerType(type);
		}
		
		if(existsUsername(user.getUsername())) {
			return null;
		}
		
		Integer maxId = -1;
		for (int id : users.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		user.setId(maxId);
		users.put(user.getId(), user);
		saveToFile();
		return user;
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	
	public void loadUsers(String contextPath) {
		this.contextPath = contextPath;
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/users.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {

					int id = Integer.parseInt(st.nextToken().trim());
					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					String name = st.nextToken().trim();
					String surname = st.nextToken().trim();
					
					int gender = Integer.parseInt(st.nextToken().trim());
					UserGenderEnum[] genders = UserGenderEnum.values();
					UserGenderEnum genderFromFile = genders[gender];
					
					LocalDate birthday = DateHelper.stringToDate(st.nextToken().trim());
					
					int role = Integer.parseInt(st.nextToken().trim());
					UserTypeEnum roleFromFile = null;
					if(role != -1) {
						UserTypeEnum[] roles = UserTypeEnum.values();
						roleFromFile = roles[role];
					}
					
							
					ArrayList<TrainingHistory>history = new ArrayList<TrainingHistory>();
					
					int membershipID = Integer.parseInt(st.nextToken().trim());
					Membership membership = null;
					if(membershipID != -1) {
						membership = new Membership(membershipID);						
					}
					
					int sportObjectID = Integer.parseInt(st.nextToken().trim());
					SportObject sportObject = null;
					
					if (sportObjectID != -1) {
						sportObject = new SportObject(sportObjectID);
					}
					
					
					ArrayList<SportObject> visitedObjects = new ArrayList<SportObject>();
					
					int collected_points = Integer.parseInt(st.nextToken().trim());
					
					int buyerTypeID = Integer.parseInt(st.nextToken().trim());
					BuyerType buyerType = new BuyerType(buyerTypeID);
					
					users.put(id, new User(id,username,password,name,surname,genderFromFile,birthday,roleFromFile,history,membership,sportObject,visitedObjects,collected_points,buyerType));

				}
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();             
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {
			File file = new File(contextPath + "/Baza/users.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(User user : users.values()) {
				out.write(user.fileLine() + '\n');
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();             
		} finally {
			if (out != null) {
				try {
					out.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	public boolean existsUsername(String username) {
		for(User user : users.values()) {
			if(user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public User change(User user) {
		users.put(user.getId(), user);
		if(user.getSportObject() != null) {
			int id = user.getSportObject().getId();
			SportObject object = SportObjectDAO.getInstance().find(id);
			user.setSportObject(object);
		}
		if(user.getMembership() != null) {
			int id = user.getMembership().getId();
			Membership mem = MembershipDAO.getInstance().find(id);
			user.setMembership(mem);
		}
		saveToFile();
		return user;
	}
	
	public User delete(int id) {
		return users.remove(id);
	}
	
	public void linkUserAndMembership() {
		ArrayList<Membership> memberships = new ArrayList<Membership>( MembershipDAO.getInstance().findAll());
		
		for(User user : users.values()) {
			if(user.getMembership() == null) {
				continue;
			}
			int requiredId = user.getMembership().getId();
			
			for(Membership m : memberships) {
				if(m.getId() == requiredId) {
					user.setMembership(m);
					m.setBuyer(user);
					break;
				}
			}
		}
	}
	
	public void linkUserAndSportObject() {
		ArrayList<SportObject> sportObjects = new ArrayList<SportObject>(SportObjectDAO.getInstance().findAll());
		
		for(User user : users.values()) {
			if (user.getSportObject() == null) {
				continue;
			}
			int requiredId = user.getSportObject().getId();
			
			for(SportObject so : sportObjects) {
				if(so.getId() == requiredId) {
					user.setSportObject(so);
					break;
				}
			}
		}
	}
	
	public void linkUserAndBuyerType() {
		ArrayList<BuyerType> buyerTypes = new ArrayList<BuyerType>(BuyerTypeDAO.getInstance().findAll());
		
		for(User user : users.values()) {
			int requiredId = user.getBuyerType().getId();
			
			for(BuyerType bt : buyerTypes) {
				if(bt.getId() == requiredId) {
					user.setBuyerType(bt);
					break;
				}
			}
		}
	}
	
	public void linkUserAndVisitedObject(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/UserVisitedObjectBound.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					int userId = Integer.parseInt(st.nextToken().trim());
					int objectId = Integer.parseInt(st.nextToken().trim());
					User user = find(userId);
					SportObject sObject = SportObjectDAO.getInstance().find(objectId);
					
					user.getVisitedObject().add(sObject);
				}
			
			}
		} catch (Exception ex) {
			ex.printStackTrace();             
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	public ArrayList<User> getAllFreeManagers(){
		ArrayList<User> freeManagers = new ArrayList<User>();
		
		for(User user : users.values()) {
			if(user.getUserType() == UserTypeEnum.Manager) {
				if(user.getSportObject() == null) {
					freeManagers.add(user);
				}
			}
		}
		return freeManagers;
		
	}
	
	public ArrayList<User> getTrainersForSportObject(int objectId){
		ArrayList<User> trainers = new ArrayList<>();
		
		for(User u : users.values()) {
			for(TrainingHistory th : u.getTrainingHistory()) {
				if(th.getTraining().getSportObject().getId() == objectId) {
					if(!trainers.contains(u)) {
						trainers.add(th.getTraining().getCoach());
					}
				}
			}
		}
		
		return trainers;
	}
	
	public ArrayList<User> getBuyersForSportObject(int objectId){
		ArrayList<User> trainers = new ArrayList<>();
		
		for(User u : users.values()) {
			for(SportObject so : u.getVisitedObject()) {
				if(so.getId() == objectId) {
					if(!trainers.contains(u)) {
						trainers.add(u);
					}
				}
			}
		}
		
		return trainers;
	}
	
	public ArrayList<User> getTCoaches() {
		ArrayList<User> trainers = new ArrayList<User>();

		for (User user : users.values()) {
			if (user.getUserType() == UserTypeEnum.Coach) {
				trainers.add(user);
			}
		}
		return trainers;
	}
}