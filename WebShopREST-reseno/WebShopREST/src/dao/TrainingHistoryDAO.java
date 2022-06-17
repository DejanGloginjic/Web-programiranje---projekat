package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.SportObject;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import beans.Enums.LocalDateTimeHelper;
import beans.Enums.TrainingTypeEnum;

/***
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class TrainingHistoryDAO {
	
	private static TrainingHistoryDAO instance = null;
	
	private Map<Integer, TrainingHistory> trainings = new HashMap<>();
	
	
	private TrainingHistoryDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	private TrainingHistoryDAO(String contextPath) {
		loadTrainingHistory(contextPath);
	}
	
	public static TrainingHistoryDAO getInstace() {
		if(instance == null) {
			instance = new TrainingHistoryDAO();
		}
		
		return instance;
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public TrainingHistory find(int id) {
		return trainings.get(id);
	}
	
	public Collection<TrainingHistory> findAll() {
		return trainings.values();
	}
	
	public TrainingHistory save(TrainingHistory trainingHistory) {
		Integer maxId = -1;
		for (int id : trainings.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		trainingHistory.setId(maxId);
		trainings.put(trainingHistory.getId(), trainingHistory);
		return trainingHistory;
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadTrainingHistory(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/trainingHistory.txt");
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
					LocalDateTime treningEntrance = LocalDateTimeHelper.stringToDate(st.nextToken().trim());
					Training training = new Training(Integer.parseInt(st.nextToken().trim()));
					User buyer = new User(Integer.parseInt(st.nextToken().trim()));
					User coach = new User(Integer.parseInt(st.nextToken().trim()));
					
					trainings.put(id, new TrainingHistory(id, treningEntrance, training, buyer, coach));
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
