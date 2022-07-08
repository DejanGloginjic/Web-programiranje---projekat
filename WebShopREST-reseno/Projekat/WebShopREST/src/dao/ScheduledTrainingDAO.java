package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.BuyerType;
import beans.Membership;
import beans.ScheduledTraining;
import beans.SportObject;
import beans.Training;
import beans.User;
import beans.Enums.DateHelper;
import beans.Enums.LocalDateTimeHelper;
import beans.Enums.ScheduledTrainingStatus;
import beans.Enums.SportObjectStatusEnum;
import beans.Enums.TrainingTypeEnum;

/***
 * <p>
 * Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima
 * (poput pretrage). Korisnici se nalaze u fajlu WebContent/users.txt u obliku:
 * <br>
 * firstName;lastName;email;username;password
 * </p>
 * <p>
 * <b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu
 * tekstualnom obliku.
 * </p>
 * 
 * @author Lazar
 *
 */
public class ScheduledTrainingDAO {

	private static ScheduledTrainingDAO instance = null;
	private static String contextPath = "";

	private Map<Integer, ScheduledTraining> trainings = new HashMap<>();

	private ScheduledTrainingDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo
	 *                    iz servleta.
	 */
	private ScheduledTrainingDAO(String contextPath) {
		loadTrainings(contextPath);
	}

	public static ScheduledTrainingDAO getInstance() {
		if (instance == null) {
			instance = new ScheduledTrainingDAO();
		}

		return instance;
	}

	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik
	 * ne postoji
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public ScheduledTraining find(int id) {
		return trainings.get(id);
	}

	public Collection<ScheduledTraining> findAll() {
		return trainings.values();
	}

	public ScheduledTraining save(ScheduledTraining training) {
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
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu
	 * {@link #users}. Kljuè je korisnièko ime korisnika.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadTrainings(String contextPath) {
		this.contextPath = contextPath;
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/scheduledTrainings.txt");
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
					Training training = new Training(Integer.parseInt(st.nextToken().trim()));
					User buyer = new User(Integer.parseInt(st.nextToken().trim()));
					LocalDateTime trainingDateAndTime = LocalDateTimeHelper.stringToDate(st.nextToken().trim());
					LocalDate dateOfApplication = DateHelper.stringToDate(st.nextToken().trim());
					
					int status = Integer.parseInt(st.nextToken().trim());
					ScheduledTrainingStatus[] statuses = ScheduledTrainingStatus.values();
					ScheduledTrainingStatus statusfromFile = statuses[status];

					trainings.put(id, new ScheduledTraining(id, training, buyer, trainingDateAndTime, dateOfApplication, statusfromFile));
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {
			File file = new File(contextPath + "/Baza/scheduledTrainings.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(ScheduledTraining training : trainings.values()) {
				out.write(training.fileLine() + '\n');
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

	public ScheduledTraining change(ScheduledTraining training) {
		trainings.put(training.getId(), training);
		return training;
	}

	public ScheduledTraining delete(int id) {
		return trainings.remove(id);
	}
	
	public ArrayList<ScheduledTraining> getPesonalTrainingsForCoach(int idUser){
		ArrayList<ScheduledTraining> pesonalTrainingsForCoach = new ArrayList<ScheduledTraining>();
		for(ScheduledTraining training : trainings.values()) {
			if(training.getTraining().getCoach() != null) {
				if((training.getTraining().getCoach().getId() == idUser) && (training.getTraining().getTrainingType().equals(TrainingTypeEnum.Personal))) {
					pesonalTrainingsForCoach.add(training);
				}
			}
		}
		return pesonalTrainingsForCoach;
	}
	
	public ArrayList<ScheduledTraining> getGroupTrainingsForCoach(int idUser){
		ArrayList<ScheduledTraining> groupTrainingsForCoach = new ArrayList<ScheduledTraining>();
		for(ScheduledTraining training : trainings.values()) {
			if(training.getTraining().getCoach() != null) {
				if((training.getTraining().getCoach().getId() == idUser) && (training.getTraining().getTrainingType().equals(TrainingTypeEnum.Group))) {
					groupTrainingsForCoach.add(training);
				}
			}
		}
		return groupTrainingsForCoach;
	}

	public void linkScheduledTrainingAndTraining() {
		ArrayList<Training> allTrainings = new ArrayList<>(TrainingDAO.getInstance().findAll());

		for (ScheduledTraining st : trainings.values()) {
			int requiredId = st.getTraining().getId();

			for (Training t : allTrainings) {
				if (t.getId() == requiredId) {
					st.setTraining(t);
					break;
				}
			}
		}
	}
	
	public void linkScheduledTrainingAndBuyer() {
		ArrayList<User> buyers = new ArrayList<User>(UserDAO.getInstance().findAll());

		for (ScheduledTraining st : trainings.values()) {
			if (st.getBuyer() == null) {
				continue;
			}
			int requiredId = st.getBuyer().getId();

			for (User u : buyers) {
				if (u.getId() == requiredId) {
					st.setBuyer(u);
					break;
				}
			}
		}
	}
	
	public ArrayList<ScheduledTraining> getTrainingsForCoach(int coachId){
		ArrayList<ScheduledTraining> retList = new ArrayList<>();
		
		for(ScheduledTraining t : trainings.values()) {
			if(t.getTraining().getCoach().getId() == coachId) {
				retList.add(t);
			}
		}
		
		return retList;
	}
}
