package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import beans.Enums.ObjectTypeEnum;
import beans.Enums.SportObjectStatusEnum;
import java.time.LocalDate;
import beans.Enums.DateHelper;
import beans.BuyerType;
import beans.Location;
import java.time.LocalTime;
import beans.Enums.LocalTimeHelper;
import java.util.ArrayList;
import beans.Enums.TrainingTypeEnum;

import beans.SportObject;
import beans.User;

/***
 * <p>Klasa namenjena da u�ita korisnike iz fajla i pru�a operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u �istu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class SportObjectDAO {
	
	private static SportObjectDAO instance = null;
	
	private Map<Integer, SportObject> sportObjects = new HashMap<>();
	
	
	private SportObjectDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	private SportObjectDAO(String contextPath) {
		loadSportObjects(contextPath);
	}
	
	
	public static SportObjectDAO getInstance() {
		if(instance == null) {
			instance = new SportObjectDAO();
		}
		
		return instance;
	}
	
	public SportObject find(int id) {
		return sportObjects.get(id);
	}
	
	public Collection<SportObject> findAll() {
		return sportObjects.values();
	}
	
	public ArrayList<SportObject> search(String filter, String criterium){
		ArrayList<SportObject> sportObjects = new ArrayList<SportObject>();
		for(SportObject sObject : findAll()) {
			if(criterium.equals("Name")){
				if(sObject.getObjectName().toLowerCase().contains(filter.toLowerCase())) {
					sportObjects.add(sObject);
				}
			}
			
			if(criterium.equals("Location")) {
				if(sObject.getLocation().getPlace().toLowerCase().contains(filter.toLowerCase())) {
					sportObjects.add(sObject);
				}
			}
			
			if(criterium.equals("Average grade")) {
				if(sObject.getObjectMark() == Double.parseDouble(filter)) {
					sportObjects.add(sObject);
				}
			}
			
			if(criterium.equals("Object type")) {
				if(sObject.getObjectType().toString().contains(filter)){
					sportObjects.add(sObject);
				}
			}
		}
		return sportObjects;
	}
	
	public SportObject save(SportObject sportobject) {
		Integer maxId = -1;
		for (int id : sportObjects.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		sportobject.setId(maxId);
		sportObjects.put(sportobject.getId(), sportobject);
		return sportobject;
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadSportObjects(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/objects.txt");
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
					
					String objectName = st.nextToken().trim();
					
					
					int type = Integer.parseInt(st.nextToken().trim());
					ObjectTypeEnum[] types = ObjectTypeEnum.values();
					ObjectTypeEnum typefromFile = types[type];
					
					int status = Integer.parseInt(st.nextToken().trim());
					SportObjectStatusEnum[] statuses = SportObjectStatusEnum.values();
					SportObjectStatusEnum statusfromFile = statuses[status];
					
					ArrayList<TrainingTypeEnum> trainingType = new ArrayList<>();
					
					
					Location location = new Location(Integer.parseInt(st.nextToken().trim()));
					String picture = st.nextToken().trim();
					
					double objectMark = Double.parseDouble(st.nextToken().trim());
					
					LocalTime startTime = LocalTimeHelper.stringToDate(st.nextToken().trim());
					LocalTime endTime = LocalTimeHelper.stringToDate(st.nextToken().trim());
				
					
					
					sportObjects.put(id, new SportObject(id, objectName, typefromFile, trainingType ,statusfromFile, location, picture, objectMark, startTime,
							endTime));
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
		
		public SportObject change(SportObject sportobject) {
			sportObjects.put(sportobject.getId(), sportobject);
			return sportobject;
		}
		
		public SportObject delete(int id) {
			return sportObjects.remove(id);
		}
		
	public void linkSportObjectAndLocation() {
		ArrayList<Location> locations = new ArrayList<Location>(LocationDAO.getInstace().findAll());
		
		for(SportObject so : sportObjects.values()) {
			int requiredId = so.getLocation().getId();
			
			for(Location l : locations) {
				if(l.getId() == requiredId) {
					so.setLocation(l);
					break;
				}
			}
		}
	}
}
