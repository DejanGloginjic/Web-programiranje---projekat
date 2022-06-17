package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Comment;
import beans.SportObject;
import beans.Training;
import beans.User;
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
		return comment;
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
					int id = Integer.parseInt(st.nextToken().trim());
					User buyerComment = new User(Integer.parseInt(st.nextToken().trim()));
					SportObject sportObjectComment = new SportObject(Integer.parseInt(st.nextToken().trim()));
					String comment = st.nextToken().trim();
					int commentMark = Integer.parseInt(st.nextToken().trim());
					
					comments.put(id, new Comment(id, buyerComment, sportObjectComment, comment, commentMark));
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
	
}
