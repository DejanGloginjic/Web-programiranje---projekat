package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.BuyerType;
import beans.Comment;
import beans.SportObject;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import beans.Enums.CommentStatusEnum;
import beans.Enums.SportObjectStatusEnum;
import beans.Enums.TrainingTypeEnum;

/***
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class CommentDAO {
	
	private static CommentDAO instance = null;
	private static String contextPath = "";
	
	private Map<Integer, Comment> comments = new HashMap<>();
	
	private CommentDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	private CommentDAO(String contextPath) {
		loadComments(contextPath);
	}
	
	public static CommentDAO getInstace() {
		if(instance == null) {
			instance = new CommentDAO();
		}
		
		return instance;
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
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
		saveToFile();
		return comment;
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadComments(String contextPath) {
		this.contextPath = contextPath;
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
					int id = Integer.parseInt(st.nextToken().trim());
					User buyerComment = new User(Integer.parseInt(st.nextToken().trim()));
					SportObject sportObjectComment = new SportObject(Integer.parseInt(st.nextToken().trim()));
					String comment = st.nextToken().trim();
					int commentMark = Integer.parseInt(st.nextToken().trim());
					
					int status = Integer.parseInt(st.nextToken().trim());
					CommentStatusEnum[] statuses = CommentStatusEnum.values();
					CommentStatusEnum statusfromFile = statuses[status];
					
					comments.put(id, new Comment(id, buyerComment, sportObjectComment, comment, commentMark, statusfromFile));
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
	
	public Comment change(Comment comment) {
		comments.put(comment.getId(), comment);
		saveToFile();
		return comment;
	}
	
	public Comment delete(int id) {
		return comments.remove(id);
	}
	
	public void linkCommentAndUser() {
		ArrayList<User> users = new ArrayList<User>(UserDAO.getInstance().findAll());
		
		for(Comment c : comments.values()) {
			int requiredId = c.getBuyerComment().getId();
			
			for(User u : users) {
				if(u.getId() == requiredId) {
					c.setBuyerComment(u);
					break;
				}
			}
		}
	}
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {
			File file = new File(contextPath + "/Baza/comments.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(Comment comment : comments.values()) {
				out.write(comment.fileLine() + '\n');
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
	
	
	
	public void linkCommentAndSportObject() {
		ArrayList<SportObject> objects = new ArrayList<SportObject>(SportObjectDAO.getInstance().findAll());
		
		for(Comment c : comments.values()) {
			int requiredId = c.getSportObjectComment().getId();
			boolean nasao = false;
			for(SportObject so : objects) {
				if(requiredId == so.getId()) {
					c.setSportObjectComment(so);
					nasao = true;
					break;
				}
			}
			if(nasao == false) {
				c.setSportObjectComment(null);			//ovo ce trebati za posle kontrolne
			}
		}
	}
	
	public boolean isUserCommentSportObject(int objectId, int userId){
		for(Comment c : comments.values()) {
			if(c.getBuyerComment().getId() == userId && c.getSportObjectComment().getId() == objectId) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Comment> getCommentsForBuyer(int objectId){
		ArrayList<Comment> retList = new ArrayList<>();
		
		for(Comment c : comments.values()) {
			if(c.getStatus() == CommentStatusEnum.Accepted && c.getSportObjectComment().getId() == objectId) {
				retList.add(c);
			}
		}
		return retList;
	}
	
	public ArrayList<Comment> getCommentsForManagerAndAdministrator(int objectId){
		ArrayList<Comment> retList = new ArrayList<>();
		
		for(Comment c : comments.values()) {
			if((c.getStatus() == CommentStatusEnum.Accepted || c.getStatus() == CommentStatusEnum.Rejected) && c.getSportObjectComment().getId() == objectId) {
				retList.add(c);
			}
		}
		return retList;
	}
	
	public ArrayList<Comment> getOnHoldComments(int objectId){
		ArrayList<Comment> retList = new ArrayList<>();
		
		for(Comment c : comments.values()) {
			if(c.getStatus() == CommentStatusEnum.OnHold && c.getSportObjectComment().getId() == objectId) {
				retList.add(c);
			}
		}
		return retList;
	}
	
}
