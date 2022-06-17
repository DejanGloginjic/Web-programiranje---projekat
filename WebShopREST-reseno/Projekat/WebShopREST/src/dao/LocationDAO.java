package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Location;
import beans.SportObject;
import beans.Training;
import beans.User;
import beans.Enums.TrainingTypeEnum;

/***
 * Klasa namenjena da uèita proizvode iz fajla i pruža operacije nad njima
 * (poput pretrage). Proizvodi se nalaze u fajlu WebContent/products.txt u
 * obliku: <br>
 * id;naziv;jedinicna cena
 * 
 * @author Lazar
 *
 */
public class LocationDAO {
	
	private static LocationDAO instance = null;

	private HashMap<Integer, Location> locations = new HashMap<Integer, Location>();

	private LocationDAO() {

	}

	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo
	 *                    iz servleta.
	 */
	private LocationDAO(String contextPath) {
		loadLocations(contextPath);
	}
	
	public static LocationDAO getInstace(String contextPath) {
		if(instance == null) {
			instance = new LocationDAO(contextPath);
		}
		
		return instance;
	}

	/***
	 * Vraæa sve proizvode.
	 * 
	 * @return
	 */
	public Collection<Location> findAll() {
		return locations.values();
	}

	/***
	 * Vraca proizvod na osnovu njegovog id-a.
	 * 
	 * @return Proizvod sa id-em ako postoji, u suprotnom null
	 */
	public Location findLocation(int id) {
		return locations.containsKey(id) ? locations.get(id) : null;
	}

	/***
	 * Dodaje proizvod u mapu proizvoda. Id novog proizvoda æe biti postavljen na
	 * maxPostojeciId + 1.
	 * 
	 * @param product
	 */
	public Location save(Location location) {
		Integer maxId = -1;
		for (int id : locations.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		location.setId(maxId);
		locations.put(location.getId(), location);
		return location;
	}

	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu
	 * {@link #products}. Kljuè je id proizovda.
	 * 
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadLocations(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/locations.txt");
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
					double longitude = Double.parseDouble(st.nextToken().trim());
					double latitude = Double.parseDouble(st.nextToken().trim());
					String street = st.nextToken().trim();
					String number = st.nextToken().trim();
					String place = st.nextToken().trim();
					String zipCode = st.nextToken().trim();
					
					locations.put(id, new Location(id, longitude, latitude, street, number, place, zipCode));
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

	public Location change(Location location) {
		locations.put(location.getId(), location);
		return location;
	}

	public Location delete(int id) {
		return locations.remove(id);
	}

}
