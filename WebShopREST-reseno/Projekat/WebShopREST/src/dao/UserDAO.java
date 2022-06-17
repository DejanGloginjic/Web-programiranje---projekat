package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	
	private Map<Integer, User> users = new HashMap<>();
	
	
	private UserDAO() {
		
	}
	
	/*
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	private UserDAO(String contextPath) {
		loadUsers(contextPath);
	}
	
	public static UserDAO getInstance(String contextPath) {
		if(userInstance == null) {
			userInstance = new UserDAO(contextPath);
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
		if (!users.containsKey(username)) {
			return null;
		}
		User user = users.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
	

	public User find(int id) {
		return users.get(id);
	}
	
	public Collection<User> findAll() {
		return users.values();
	}
	
	
	public User save(User user) {
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
		return user;
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	
	public void loadUsers(String contextPath) {
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

					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					String name = st.nextToken().trim();
					String surname = st.nextToken().trim();
					
					int gender = Integer.parseInt(st.nextToken().trim());
					UserGenderEnum[] genders = UserGenderEnum.values();
					UserGenderEnum genderFromFile = genders[gender];
					
					LocalDate birthday = DateHelper.stringToDate(st.nextToken().trim());
					
					int role = Integer.parseInt(st.nextToken().trim());
					UserTypeEnum[] roles = UserTypeEnum.values();
					UserTypeEnum roleFromFile = roles[role];
							
					ArrayList<TrainingHistory>history = new ArrayList();
					
					int membershipID = Integer.parseInt(st.nextToken().trim());
					Membership membership = new Membership(membershipID);
					
					int sportObjectID = Integer.parseInt(st.nextToken().trim());
					SportObject sportObject = new SportObject(sportObjectID);
					
					ArrayList<SportObject> visitedObjects = new ArrayList();
					
					int collected_points = Integer.parseInt(st.nextToken().trim());
					
					int buyerTypeID = Integer.parseInt(st.nextToken().trim());
					BuyerType buyerType = new BuyerType(buyerTypeID);
					
					int id = Integer.parseInt(st.nextToken().trim());
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
	public User change(User user) {
		users.put(user.getId(), user);
		return user;
	}
	
	public User delete(int id) {
		return users.remove(id);
	}
	
	public void linkUserAndMembership(String contextPath) {
		ArrayList<Membership> memberships = (ArrayList<Membership>) MembershipDAO.getInstance(contextPath).findAll();
		
		for(User user : users.values()) {
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
	
	public void linkUserAndSportObject(String contextPath) {
		ArrayList<SportObject> sportObjects = (ArrayList<SportObject>) SportObjectDAO.getInstance(contextPath).findAll();
		
		for(User user : users.values()) {
			int requiredId = user.getSportObject().getId();
			
			for(SportObject so : sportObjects) {
				if(so.getId() == requiredId) {
					user.setSportObject(so);
					break;
				}
			}
		}
	}
	
	public void linkUserAndBuyerType(String contextPath) {
		ArrayList<BuyerType> buyerTypes = (ArrayList<BuyerType>) BuyerTypeDAO.getInstance(contextPath).findAll();
		
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
}