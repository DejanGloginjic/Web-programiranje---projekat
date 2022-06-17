package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.Membership;
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
public class TrainingDAO {
	
	private static TrainingDAO instance = null;
	
	private Map<Integer, Training> trainings = new HashMap<>();	
	
	private TrainingDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	private TrainingDAO(String contextPath) {
		loadTrainings(contextPath);
	}
	
	public static TrainingDAO getInstance(String contextPath) {
		if(instance == null) {
			instance = new TrainingDAO(contextPath);
		}
		
		return instance;
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public Training find(int id) {
		return trainings.get(id);
	}
	
	public Collection<Training> findAll() {
		return trainings.values();
	}
	
	public Training save(Training training) {
		Integer maxId = -1;
		for (int id : trainings.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		training.setId(maxId);
		trainings.put(training.getId(), training);
		return training;
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadTrainings(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/users.txt");
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
					String trainingName = st.nextToken().trim();
					
					int trainingType = Integer.parseInt(st.nextToken().trim());
					TrainingTypeEnum[] types = TrainingTypeEnum.values();
					TrainingTypeEnum typesFromFile = types[trainingType];
					
					SportObject sportObject = new SportObject(Integer.parseInt(st.nextToken().trim()));
					int duration = Integer.parseInt(st.nextToken().trim());
					User coach = new User(Integer.parseInt(st.nextToken().trim()));
					String description = st.nextToken().trim();
					String image = st.nextToken().trim();
					
					trainings.put(id, new Training(id, trainingName, typesFromFile, sportObject, duration, coach, description, image));
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
	
	public Training change(Training training) {
		trainings.put(training.getId(), training);
		return training;
	}
	
	public Training delete(int id) {
		return trainings.remove(id);
	}
	
	public void linkTrainingAndCoach(String contextPath) {
		ArrayList<User> coaches = (ArrayList<User>) UserDAO.getInstance(contextPath).findAll();
		
		for(Training t : trainings.values()) {
			int requiredId = t.getCoach().getId();
			
			for(User u : coaches) {
				if(u.getId() == requiredId) {
					t.setCoach(u);
					break;
				}
			}
		}
	}
	
	public void linkTrainingAndSportObject(String contextPath) {
		ArrayList<SportObject> objects = (ArrayList<SportObject>) SportObjectDAO.getInstance(contextPath).findAll();
		
		for(Training t : trainings.values()) {
			int requiredId = t.getSportObject().getId();
			
			for(SportObject so : objects) {
				if(so.getId() == requiredId) {
					t.setSportObject(so);
					break;
				}
			}
		}
	}
}
