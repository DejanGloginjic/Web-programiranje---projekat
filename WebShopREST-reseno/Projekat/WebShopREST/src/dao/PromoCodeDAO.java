package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import beans.BuyerType;
import beans.Comment;
import beans.PromoCode;
import beans.SportObject;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import beans.Enums.CommentStatusEnum;
import beans.Enums.DateHelper;
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
public class PromoCodeDAO {
	
	private static PromoCodeDAO instance = null;
	private static String contextPath = "";
	
	private Map<Integer, PromoCode> codes = new HashMap<>();
	
	private PromoCodeDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	private PromoCodeDAO(String contextPath) {
		loadPromoCodes(contextPath);
	}
	
	public static PromoCodeDAO getInstace() {
		if(instance == null) {
			instance = new PromoCodeDAO();
		}
		
		return instance;
	}
	
	/**
	 * Vraæa korisnika za prosleðeno korisnièko ime i šifru. Vraæa null ako korisnik ne postoji
	 * @param username
	 * @param password
	 * @return
	 */
	public PromoCode find(int id) {
		return codes.get(id);
	}
	
	public Collection<PromoCode> findAll() {
		return codes.values();
	}
	
	public PromoCode getByCode(String code) {
		for(PromoCode pc : codes.values()) {
			if(pc.getCode().equals(code)) {
				return pc;
			}
		}
		return null;
	}
	
	public PromoCode save(PromoCode code) {
		Integer maxId = -1;
		for (int id : codes.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		code.setId(maxId);
		codes.put(code.getId(), code);
		saveToFile();
		return code;
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadPromoCodes(String contextPath) {
		this.contextPath = contextPath;
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/promoCodes.txt");
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
					String code = st.nextToken().trim();
					LocalDate startDate = DateHelper.stringToDate(st.nextToken().trim());
					LocalDate endDate = DateHelper.stringToDate(st.nextToken().trim());
					int numberOfUses = Integer.parseInt(st.nextToken().trim());
					int percentage = Integer.parseInt(st.nextToken().trim());
					
					codes.put(id, new PromoCode(id, code, startDate, endDate, numberOfUses, percentage));
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
	
	public PromoCode change(PromoCode code) {
		codes.put(code.getId(), code);
		saveToFile();
		return code;
	}
	
	public PromoCode delete(int id) {
		return codes.remove(id);
	}
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {
			File file = new File(contextPath + "/Baza/promoCodes.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(PromoCode code : codes.values()) {
				out.write(code.fileLine() + '\n');
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
	
}
