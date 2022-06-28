package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.BuyerType;
import beans.Membership;
import beans.SportObject;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import beans.Enums.LocalDateTimeHelper;
import beans.Enums.TrainingTypeEnum;

/***
 * <p>Klasa namenjena da u�ita korisnike iz fajla i pru�a operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u �istu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class TrainingHistoryDAO {
	
	private static TrainingHistoryDAO instance = null;
	private static String contextPath = "";
	
	private Map<Integer, TrainingHistory> trainings = new HashMap<Integer, TrainingHistory>();
	
	
	private TrainingHistoryDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
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
	 * Vra�a korisnika za prosle�eno korisni�ko ime i �ifru. Vra�a null ako korisnik ne postoji
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
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadTrainingHistory(String contextPath) {
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
					
					int coachId = Integer.parseInt(st.nextToken().trim());
					User coach = null;
					if (coachId != -1) {
						coach = new User(Integer.parseInt(st.nextToken().trim()));
					}
					
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
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {
			File file = new File(contextPath + "/Baza/trainingHistory.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(TrainingHistory th : trainings.values()) {
				out.write(th.fileLine() + '\n');
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
	
	public TrainingHistory change(TrainingHistory training) {
		trainings.put(training.getId(), training);
		return training;
	}
	
	public TrainingHistory delete(int id) {
		return trainings.remove(id);
	}
	
	public void linkTrainingHistoryAndTraining() {
		ArrayList<Training> allTrainings = new ArrayList<Training>(TrainingDAO.getInstance().findAll());
		
		for(TrainingHistory th :  trainings.values()) {
			int requiredId = th.getTraining().getId();
			
			for(Training t : allTrainings) {
				if(t.getId() == requiredId) {
					th.setTraining(t);
					break;
				}
			}
		}
	}
	
	public void linkTrainingHistoryAndBuyer() {
		ArrayList<User> buyers = new ArrayList<User>(UserDAO.getInstance().findAll());
		
		for(TrainingHistory th : trainings.values()) {
			int requiredId = th.getBuyer().getId();
			
			for(User u : buyers) {
				if(u.getId() == requiredId) {
					th.setBuyer(u);
					u.addTrainingToTrainingHistory(th);
					break;
				}
			}
		}
	}
	
	public void linkTrainingHistoryAndCoach() {
		ArrayList<User> coaches = new ArrayList<User>(UserDAO.getInstance().findAll());
		
		for(TrainingHistory th : trainings.values()) {
			if (th.getCoach() == null) {
				continue;
			}
			int requiredId = th.getCoach().getId();
			
			for(User u : coaches) {
				if(u.getId() == requiredId) {
					th.setCoach(u);
					break;
				}
			}
		}
	}
	
}
