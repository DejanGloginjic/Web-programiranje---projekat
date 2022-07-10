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
import beans.Membership;
import beans.SportObject;
import beans.Training;
import beans.User;
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
public class TrainingDAO {

	private static TrainingDAO instance = null;
	private static String contextPath = "";

	private Map<Integer, Training> trainings = new HashMap<>();

	private TrainingDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo
	 *                    iz servleta.
	 */
	private TrainingDAO(String contextPath) {
		loadTrainings(contextPath);
	}

	public static TrainingDAO getInstance() {
		if (instance == null) {
			instance = new TrainingDAO();
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
		saveToFile();
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
			File file = new File(contextPath + "/Baza/trainings.txt");
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

					int coachId = Integer.parseInt(st.nextToken().trim());
					User coach = null;
					if (coachId != -1) {
						coach = new User(coachId);
					}

					String description = st.nextToken().trim();
					String image = st.nextToken().trim();

					trainings.put(id, new Training(id, trainingName, typesFromFile, sportObject, duration, coach,
							description, image));
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
			File file = new File(contextPath + "/Baza/trainings.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(Training training : trainings.values()) {
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

	public Training change(Training training) {
		trainings.put(training.getId(), training);
		saveToFile();
		return training;
	}

	public Training delete(int id) {
		return trainings.remove(id);
	}
	
	public ArrayList<Training> getPesonalTrainingsForCoach(int idUser){
		ArrayList<Training> pesonalTrainingsForCoach = new ArrayList<Training>();
		for(Training training : trainings.values()) {
			if(training.getCoach() != null) {
				if((training.getCoach().getId() == idUser) && (training.getTrainingType().equals(TrainingTypeEnum.Personal))) {
					pesonalTrainingsForCoach.add(training);
				}
			}
		}
		return pesonalTrainingsForCoach;
	}
	
	public ArrayList<Training> getGroupTrainingsForCoach(int idUser){
		ArrayList<Training> groupTrainingsForCoach = new ArrayList<Training>();
		for(Training training : trainings.values()) {
			if(training.getCoach() != null) {
				if((training.getCoach().getId() == idUser) && (training.getTrainingType().equals(TrainingTypeEnum.Group))) {
					groupTrainingsForCoach.add(training);
				}
			}
		}
		return groupTrainingsForCoach;
	}

	public void linkTrainingAndCoach() {
		ArrayList<User> coaches = new ArrayList<User>(UserDAO.getInstance().findAll());

		for (Training t : trainings.values()) {
			if (t.getCoach() == null) {
				continue;
			}
			int requiredId = t.getCoach().getId();

			for (User u : coaches) {
				if (u.getId() == requiredId) {
					t.setCoach(u);
					break;
				}
			}
		}
	}
	
	public ArrayList<Training> getTrainingForSportObject(int sportObjectID){
		ArrayList<Training> foundTrainings = new ArrayList<Training>();
		
		for(Training training : trainings.values()) {
			if(training.getSportObject().getId() == sportObjectID) {
				foundTrainings.add(training);
			}
		}
		return foundTrainings;
	}

	public void linkTrainingAndSportObject() {
		ArrayList<SportObject> objects = new ArrayList<SportObject>(SportObjectDAO.getInstance().findAll());

		for (Training t : trainings.values()) {
			int requiredId = t.getSportObject().getId();

			for (SportObject so : objects) {
				if (so.getId() == requiredId) {
					t.setSportObject(so);
					break;
				}
			}
		}
	}
	
	public ArrayList<Training> getTrainingsForCoach(int coachId){
		ArrayList<Training> retList = new ArrayList<>();
		
		for(Training t : trainings.values()) {
			if(t.getCoach().getId() == coachId) {
				retList.add(t);
			}
		}
		
		return retList;
	}
}
