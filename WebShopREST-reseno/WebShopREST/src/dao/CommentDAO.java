package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
<<<<<<< HEAD
=======
<<<<<<<< HEAD:WebShopREST-reseno/WebShopREST/src/dao/CommentDAO.java
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb

import beans.Comment;
import beans.SportObject;
import beans.Training;
import beans.User;
import beans.Enums.TrainingTypeEnum;
<<<<<<< HEAD
=======
========
import beans.Enums.UserGenderEnum;
import java.time.LocalDate;
import beans.Enums.DateHelper;
import beans.Enums.TrainingTypeEnum;
import beans.SportObject;
import beans.TrainingHistory;
import beans.User;
import beans.Membership;
import beans.SportObject;
import beans.BuyerType;
import beans.Enums.UserTypeEnum;

>>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb:WebShopREST-reseno/WebShopREST/src/dao/UserDAO.java
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb

/***
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
<<<<<<< HEAD
public class CommentDAO {
	
	private static CommentDAO instance = null;
	
	private Map<Integer, Comment> comments = new HashMap<>();
	
	private CommentDAO() {
=======
<<<<<<<< HEAD:WebShopREST-reseno/WebShopREST/src/dao/CommentDAO.java
public class CommentDAO {
========
public class UserDAO {
	
	private static UserDAO instance = null;
	
	private Map<Integer, User> users = new HashMap<>();
>>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb:WebShopREST-reseno/WebShopREST/src/dao/UserDAO.java
	
	private static CommentDAO instance = null;
	
<<<<<<<< HEAD:WebShopREST-reseno/WebShopREST/src/dao/CommentDAO.java
	private Map<Integer, Comment> comments = new HashMap<>();
	
	private CommentDAO() {
========
	private UserDAO() {
>>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb:WebShopREST-reseno/WebShopREST/src/dao/UserDAO.java
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
<<<<<<< HEAD
=======
<<<<<<<< HEAD:WebShopREST-reseno/WebShopREST/src/dao/CommentDAO.java
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
	private CommentDAO(String contextPath) {
		loadComments(contextPath);
	}
	
	public static CommentDAO getInstace() {
		if(instance == null) {
			instance = new CommentDAO();
		}
		
		return instance;
<<<<<<< HEAD
=======
========
	private UserDAO(String contextPath) {
		loadUsers(contextPath);
>>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb:WebShopREST-reseno/WebShopREST/src/dao/UserDAO.java
	}
	
	public static UserDAO getInstance() {
		if(instance == null) {
			instance = new UserDAO();
		}
		
		return instance;
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
<<<<<<< HEAD
=======
<<<<<<<< HEAD:WebShopREST-reseno/WebShopREST/src/dao/CommentDAO.java
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
	public Comment find(int id) {
		return comments.get(id);
	}
	
	public Collection<Comment> findAll() {
		return comments.values();
	}
	
	public Comment save(Comment comment) {
		Integer maxId = -1;
		for (int id : comments.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		comment.setId(maxId);
		comments.put(comment.getId(), comment);
		return comment;
<<<<<<< HEAD
=======
========
	public User validation(String username, String password) {
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
>>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb:WebShopREST-reseno/WebShopREST/src/dao/UserDAO.java
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
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadComments(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/comments.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
<<<<<<< HEAD
=======
<<<<<<<< HEAD:WebShopREST-reseno/WebShopREST/src/dao/CommentDAO.java
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
					int id = Integer.parseInt(st.nextToken().trim());
					User buyerComment = new User(Integer.parseInt(st.nextToken().trim()));
					SportObject sportObjectComment = new SportObject(Integer.parseInt(st.nextToken().trim()));
					String comment = st.nextToken().trim();
					int commentMark = Integer.parseInt(st.nextToken().trim());
					
<<<<<<< HEAD
					
					comments.put(id, new Comment(id, buyerComment, sportObjectComment, comment, commentMark));
=======
					comments.put(id, new Comment(id, buyerComment, sportObjectComment, comment, commentMark));
========
					
					int id = Integer.parseInt(st.nextToken().trim());
					
					String username = st.nextToken().trim();
					String password = st.nextToken().trim();
					String name = st.nextToken().trim();
					String surname = st.nextToken().trim();
					
					int gender = Integer.parseInt(st.nextToken().trim());
					UserGenderEnum[] genders = UserGenderEnum.values();
					UserGenderEnum genderfromFile = genders[gender];
					
					LocalDate dateofBirth = DateHelper.stringToDate(st.nextToken().trim());
					
					int type = Integer.parseInt(st.nextToken().trim());
					UserTypeEnum[] types = UserTypeEnum.values();
					UserTypeEnum typefromFile = types[type];
					
					ArrayList<TrainingHistory> trainingHistory = new ArrayList<>();
					
					
					Membership membership = new Membership(Integer.parseInt(st.nextToken().trim()));
				
					
					ArrayList<SportObject> sportObject = new ArrayList<>();
			    
				    SportObject visitedObject = new SportObject(Integer.parseInt(st.nextToken().trim()));
			        
					int points = Integer.parseInt(st.nextToken().trim());
					
					
					BuyerType buyerType = new BuyerType(Integer.parseInt(st.nextToken().trim()));
				
					
					
					users.put(id, new User(id, username, password, name, surname, genderfromFile, dateofBirth, typefromFile, trainingHistory,
						    membership, visitedObject, sportObject, points, buyerType));
>>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb:WebShopREST-reseno/WebShopREST/src/dao/UserDAO.java
>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
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
<<<<<<< HEAD
	
}
=======
		
		public User change(User user) {
			users.put(user.getId(), user);
			return user;
		}
		
		public User delete(int id) {
			return users.remove(id);
		}
		
		
}
	

>>>>>>> 02ff9393f13340d54ece7ee3ed788c8bf8b5bacb
